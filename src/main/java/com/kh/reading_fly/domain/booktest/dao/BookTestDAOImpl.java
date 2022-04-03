package com.kh.reading_fly.domain.booktest.dao;

import com.kh.reading_fly.domain.board.dto.BoardDTO;
import com.kh.reading_fly.domain.booktest.dto.BookTestDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
public class BookTestDAOImpl implements BookTestDAO {

  private final JdbcTemplate jdbcTemplate;



  @Override
  public List<BookTestDTO> selectAll() {
    //sql 작성
    StringBuffer sql = new StringBuffer();
    sql.append(" select isbn, title, thumbnail ");
    sql.append(" from book ");

    //sql 실행
    List<BookTestDTO> list = jdbcTemplate.query(sql.toString(), new BeanPropertyRowMapper<>(BookTestDTO.class));

    return list;
  }

  @Override
  public BookTestDTO selectOne(Long isbn) {
    StringBuffer sql = new StringBuffer();
    sql.append(" select isbn, title, thumbnail  ");
    sql.append(" from book ");
    sql.append(" where  isbn = ? ");

    //sql 실행
    List<BookTestDTO> query = jdbcTemplate.query(
        sql.toString(), new BeanPropertyRowMapper<>(BookTestDTO.class), isbn
    );

    return (query.size() == 1)? query.get(0) : null;
  }

  @Override
  public BookTestDTO create(BookTestDTO bookTestDTO) {
    //sql 작성
    StringBuffer sql = new StringBuffer();
    sql.append(" insert into book (isbn, title, thumbnail) ");
    sql.append(" values (?, ?, ?) ");

    //sql 실행
    KeyHolder keyHolder = new GeneratedKeyHolder();
    jdbcTemplate.update(new PreparedStatementCreator() {
      @Override
      public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
        PreparedStatement pstmt = con.prepareStatement(
            sql.toString(),
            new String[]{"isbn"}
        );

        pstmt.setString(1, String.valueOf(bookTestDTO.getIsbn()));
        pstmt.setString(2, bookTestDTO.getTitle());
        pstmt.setString(3, bookTestDTO.getThumbnail());

        return pstmt;
      }
    }, keyHolder);

    Long isbn = Long.valueOf(keyHolder.getKeys().get("bnum").toString());
    return selectOne(isbn);
  }

  @Override
  public BookTestDTO update(BookTestDTO bookTestDTO) {
    StringBuffer sql = new StringBuffer();
    sql.append(" update book ");
    sql.append(" set title = ? , ");
    sql.append("     thumbnail = ? , ");
    sql.append("     budate = systimestamp ");
    sql.append(" where isbn = ? ");

    KeyHolder keyHolder = new GeneratedKeyHolder();
    jdbcTemplate.update(new PreparedStatementCreator() {
      @Override
      public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
        PreparedStatement pstmt = con.prepareStatement(
            sql.toString(),
            new String[]{"isbn"}
        );

        pstmt.setString(1, bookTestDTO.getTitle());
        pstmt.setString(2, bookTestDTO.getThumbnail());

        return pstmt;
      }
    }, keyHolder);

    Long isbn = Long.valueOf(keyHolder.getKeys().get("isbn").toString());
    return selectOne(isbn);
  }


}
