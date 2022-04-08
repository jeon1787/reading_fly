package com.kh.reading_fly.domain.book.dao;

import com.kh.reading_fly.domain.book.dto.Book;
import com.kh.reading_fly.web.form.member.SessionConst;
import com.kh.reading_fly.web.form.member.login.LoginMember;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.stereotype.Repository;

import javax.servlet.http.HttpSession;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
public class BookDAOImpl implements BookDAO{

    private final JdbcTemplate jdbcTemplate;
    /**
     * 도서등록
     * @param book
     * @return
     */
    @Override
    public Book saveBook(Book book) {
        StringBuffer sql = new StringBuffer();
        sql.append("insert into book (isbn, title, author, publisher, translator, thumbnail, publication_dt, bcontents) ");
        sql.append("values (?, ?, ?, ?, ?, ?, ?, ?) ");
        log.info("isbn={}",book.getIsbn());
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement pstmt = con.prepareStatement(sql.toString());
                pstmt.setString(1,book.getIsbn());
                pstmt.setString(2,book.getTitle());
                pstmt.setString(3,book.getAuthors());
                pstmt.setString(4,book.getPublisher());
                pstmt.setString(5,book.getTranslators());
                pstmt.setString(6,book.getThumbnail());
                pstmt.setDate(7,book.getPublisher_dt());
                pstmt.setString(8,book.getBcontents());
                return pstmt;
            }
        });
        return book;
    }

    /**
     * 책장등록
     * @param book
     * @return
     */
    @Override
    public Long saveBookShelf(Book book) {
        StringBuffer sql = new StringBuffer();
        sql.append("insert into book_shelf (snum, spage, sisbn, sid) ");
        sql.append("values(book_shelf_snum_seq.nextval, ?, ?, ?) ");
        //SQL실행
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement pstmt = con.prepareStatement(sql.toString(),new String[]{"snum"});  // keyHolder에 담을 테이블의 컬럼명을 지정

                pstmt.setLong(1,book.getSpage());
                pstmt.setString(2,book.getSisbn());
                pstmt.setString(3,book.getSid());
                return pstmt;
            }
        },keyHolder);
        Long snum = Long.valueOf(keyHolder.getKeys().get("snum").toString());
        return snum;
    }

    /**
     * 독서기록등록
     * @param book
     * @return
     */
    @Override
    public Book saveDocument(Book book) {
        StringBuffer sql = new StringBuffer();
        sql.append("insert into document(dnum, ddate, dpage, dsnum, did) ");
        sql.append("values(document_dnum_seq.nextval, systimestamp, ?, ?, ?) ");
        //SQL실행
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement pstmt = con.prepareStatement(sql.toString());  // keyHolder에 담을 테이블의 컬럼명을 지정

                pstmt.setLong(1,book.getDpage());
                pstmt.setLong(2,book.getDsnum());
                pstmt.setString(3,book.getDid());
                return pstmt;
            }
        });
        return book;
    }

    /**
     * 목록
     * @return
     */
    @Override
    public List<Book> list(String id) {
        StringBuffer sql = new StringBuffer();
        sql.append("select book.thumbnail, book.title, book.isbn ");
        sql.append("from book , book_shelf , document ");
        sql.append("where book.isbn = book_shelf.sisbn and book_shelf.sid = document.did ");
        sql.append("and book_shelf.sid = ? " );
        sql.append("order by document.ddate " );
        List<Book> listBook = jdbcTemplate.query(sql.toString(), new BeanPropertyRowMapper<>(Book.class),id);
        log.info("books={}", listBook);
        return listBook;
    }

    /**
     * 조회
     * @param isbn
     * @return
     */
    @Override
    public List<Book> detail(String id, String isbn) {

//        클릭한 ㅣ미지(isbn), 유저(id)로 검색
//        book_shelf에서 isbn,id 로 검색한 bunm
//        bnum=dsnum이 같은 결과 출력 =====독서기록
        StringBuffer sql = new StringBuffer();
        sql.append("select document.dnum, document.ddate, document.dpage ");
        sql.append("from document, book_shelf ");
        sql.append("where book_shelf.snum = document.dsnum ");
        sql.append("and book_shelf.sid = ? ");
        sql.append("and book_shelf.sisbn = ? ");
        List<Book> detailBook = jdbcTemplate.query(sql.toString(), new BeanPropertyRowMapper<>(Book.class),
                id, isbn);

        return detailBook;
    }

    /**
     * 수정
     * @param isbn
     * @param book
     * @return
     */
    @Override
    public Long update(String id, String isbn, Book book) {
        StringBuffer sql = new StringBuffer();
        sql.append("insert into document (dnum, ddate, dpage, dsnum, did) ");
        sql.append("value (document_dnum_seq.nextval, systimestamp, ?, ?, ?) ");

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement pstmt = con.prepareStatement(sql.toString(), new String[]{"dnum"});
                pstmt.setLong(1, book.getDpage());
                pstmt.setLong(2, book.getDsnum());
                pstmt.setString(3, id);
                return pstmt;
            }
        },keyHolder);
        Long dnum = Long.valueOf(keyHolder.getKeys().get("dnum").toString());
        return dnum;
    }

    /**
     * 삭제
     * @param isbn
     * @return
     */
    @Override
    public int delete(String isbn) {
//        String sql = "delete from product where product_id = ? ";
        StringBuffer sql = new StringBuffer();
        sql.append("delete from product ");
        sql.append("where product_id = ? ");

        int result = jdbcTemplate.update(sql.toString(), isbn);

        return result;
    }
}
