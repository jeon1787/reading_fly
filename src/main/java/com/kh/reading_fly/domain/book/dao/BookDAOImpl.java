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
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
@Transactional
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
        log.info("book={}", book);
        return book;
    }

    /**
     * 책장 등록
     * @param book
     * @return
     */
    @Override
    public Long saveShelf(Book book) {
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
                pstmt.setString(2,book.getIsbn());
                pstmt.setString(3,book.getSid());
                return pstmt;
            }
        },keyHolder);
        Long snum = Long.valueOf(keyHolder.getKeys().get("snum").toString());
        log.info("snum={}", snum);
        return snum;
    }

    /**
     * 독서기록 등록(최초)
     * @param book
     * @return
     */
    @Override
    public Long saveDoc(Book book) {
        StringBuffer sql = new StringBuffer();
        sql.append("insert into document(dnum, ddate, dpage, dsnum, did) ");
        sql.append("values(document_dnum_seq.nextval, systimestamp, ?, ?, ?) ");
        //SQL실행
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement pstmt = con.prepareStatement(sql.toString(),new String[]{"dnum"});  // keyHolder에 담을 테이블의 컬럼명을 지정

                pstmt.setLong(1,book.getDpage());
                pstmt.setLong(2,book.getDsnum());
                pstmt.setString(3,book.getDid());
                return pstmt;
            }
        },keyHolder);
        Long dnum = Long.valueOf(keyHolder.getKeys().get("dnum").toString());
        log.info("dnum={}", dnum);
        return dnum;
    }

    /**
     * 책장목록
     * @param id
     * @return
     */
    @Override
    public List<Book> listShelf(String id) {
        StringBuffer sql = new StringBuffer();
        sql.append("select thumbnail, title, isbn ");
        sql.append("from book ");
        sql.append("where isbn in (select sisbn ");
        sql.append("from(select sisbn, max(ddate) " );
        sql.append("from (select document.ddate, book_shelf.sisbn " );
        sql.append("from document, book_shelf " );
        sql.append("where book_shelf.snum = document.dsnum " );
        sql.append("and book_shelf.sid = ? " );
        sql.append("order by document.ddate desc) " );
        sql.append("group by sisbn)) " );
        List<Book> listBook = jdbcTemplate.query(sql.toString(), new BeanPropertyRowMapper<>(Book.class),id);
        log.info("listBook={}", listBook);
        return listBook;
    }

    /**
     * 책장삭제
     * @param id
     * @param isbn
     * @return
     */
    @Override
    public int delShelf(String id, String isbn) {
        String sql = "delete from book_shelf where sid = ? and sisbn = ? ";
        int delShelf = jdbcTemplate.update(sql.toString(), id, isbn);
        log.info("delShelf={}", delShelf);
        return delShelf;
    }

    /**
     * 기록삭제
     * @param id
     * @param isbn
     * @return
     */
    @Override
    public int delDoc(String id, String isbn) {
        StringBuffer sql = new StringBuffer();
        sql.append("delete ");
        sql.append("from (select dnum, ddate, dpage, dsnum, did ");
        sql.append("from book_shelf, (select dnum, ddate, dpage, dsnum, did ");
        sql.append("from document ");
        sql.append("where did = ?) ");
        sql.append("where book_shelf.snum = dsnum ");
        sql.append("and book_shelf.sisbn = ? ");
        int delDoc = jdbcTemplate.update(sql.toString(), id, isbn);
        log.info("delDoc={}", delDoc);
        return delDoc;
    }

    /**
     * 최근기록 단건 조회
     * @param id
     * @param isbn
     * @return
     */
    @Override
    public Book detailDoc(String id, String isbn) {
        StringBuffer sql = new StringBuffer();
        sql.append("select dsnum, ddate, dpage, spage, thumbnail, title, isbn ");
        sql.append("from (select document.dsnum, document.dnum, document.ddate, document.dpage, book_shelf.spage, book.thumbnail, book.title, book.isbn ");
        sql.append("from document, book_shelf, book ");
        sql.append("where book_shelf.snum = document.dsnum and book_shelf.sisbn = book.isbn ");
        sql.append("and book_shelf.sid = ? ");
        sql.append("and book_shelf.sisbn = ? ");
        sql.append("order by document.ddate desc) ");
        sql.append("where rownum = 1 ");
        Book book = jdbcTemplate.queryForObject(sql.toString(), new BeanPropertyRowMapper<>(Book.class), id, isbn);
        log.info("book={}", book);
        return book;
    }

    /**
     * 기록 목록 조회
     * @param id
     * @param isbn
     * @return
     */
    @Override
    public List<Book> listDoc(String id, String isbn) {
        StringBuffer sql = new StringBuffer();
        sql.append("select document.dnum, document.ddate, document.dpage, book_shelf.spage ");
        sql.append("from document, book_shelf ");
        sql.append("where book_shelf.snum = document.dsnum ");
        sql.append("and book_shelf.sid = ? ");
        sql.append("and book_shelf.sisbn = ? ");
        sql.append("order by document.ddate desc, document.dpage desc ");
        List<Book> detailBook = jdbcTemplate.query(sql.toString(), new BeanPropertyRowMapper<>(Book.class), id, isbn);
        log.info("detailBook={}", detailBook);
        return detailBook;
    }

    /**
     * 기록 등록
     * @param book
     * @return
     */
    @Override
    public Long insertDoc(Book book) {
        StringBuffer sql = new StringBuffer();
        sql.append("insert into document (dnum, ddate, dsnum, dpage, did) ");
        sql.append("values (document_dnum_seq.nextval, ?, ?, ?, ?) ");
//        sql.append("select document_dnum_seq.nextval, ?, ?, ?, ? ");
//        sql.append("from book_shelf, document ");
//        sql.append("where book_shelf.sid = ? ");
//        sql.append("and book_shelf.sisbn = ? ");
//        log.info("book1={}", book);

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement pstmt = con.prepareStatement(sql.toString(),new String[]{"dnum"});  // keyHolder에 담을 테이블의 컬럼명을 지정

                pstmt.setDate(1, Date.valueOf(book.getDdate()));
                pstmt.setLong(2,book.getDsnum());
                pstmt.setLong(3,book.getDpage());
                pstmt.setString(4,book.getDid());
//                pstmt.setString(5,book.getSid());
//                pstmt.setString(6,book.getSisbn());
                return pstmt;
            }
        },keyHolder);
        Long dnum = Long.valueOf(keyHolder.getKeys().get("dnum").toString());
        log.info("dnum={}", dnum);
        return dnum;
    }

    /**
     * 총페이지 수정
     * @param book
     * @return
     */
    @Override
    public int editDoc(Book book) {
        StringBuffer sql = new StringBuffer();
        sql.append("update book_shelf ");
        sql.append("set spage = ? ");
        sql.append("where sid = ? ");
        sql.append("and sisbn = ? ");

        int editRow = jdbcTemplate.update(sql.toString(), book.getSpage(), book.getSid(), book.getSisbn());
        log.info("editRow={}", editRow);
        return editRow;
    }

    /**
     * 독서기록 단건 삭제
     * @param dnum
     * @return
     */
    @Override
    public int removeDoc(String id, Long dnum) {
        String sql = "delete from document where did = ? and dnum = ? ";
        int removeRow = jdbcTemplate.update(sql.toString(), id, dnum);
        log.info("removeRow={}", removeRow);
        return removeRow;
    }
}
