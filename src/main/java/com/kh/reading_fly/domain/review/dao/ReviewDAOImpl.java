package com.kh.reading_fly.domain.review.dao;

import com.kh.reading_fly.domain.comment.dto.CommentDTO;
import com.kh.reading_fly.domain.review.dto.ReviewDTO;
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
public class ReviewDAOImpl implements ReviewDAO{

  private final JdbcTemplate jdbcTemplate;

  @Override
  public List<ReviewDTO> reSelectAll(Long risbn) {
    StringBuffer sql = new StringBuffer();
    sql.append(" select row_number() over (order by rcdate) as num, rnum, risbn, rcontent, rstar, rcdate, rudate, rid, nickname ");
    sql.append(" from review, member ");
    sql.append(" where review.rid = member.id and risbn = ? ");
    sql.append(" order by rcdate asc ");

    //sql 실행
    List<ReviewDTO> list = jdbcTemplate.query(sql.toString(), new BeanPropertyRowMapper<>(ReviewDTO.class), risbn);

    return list;
  }

  @Override
  public ReviewDTO reSelectOne(Long rnum) {
    StringBuffer sql = new StringBuffer();

    sql.append(" select rnum, risbn, rcontent, rstar,rcdate, rudate, rid, nickname ");
    sql.append(" from review, member ");
    sql.append(" where review.rid = member.id and rnum = ? ");

    //sql 실행
    List<ReviewDTO> query = jdbcTemplate.query(sql.toString(),
                            new BeanPropertyRowMapper<>(ReviewDTO.class), rnum);
    return (query.size() == 1)? query.get(0) : null;
  }

  @Override
  public ReviewDTO reCreate(ReviewDTO reviewDTO) {
    StringBuffer sql = new StringBuffer();
//    sql.append(" insert into review (rnum, rcontent, rstar, risbn, rid) ");
//    sql.append(" values (review_rnum_seq.nextval, ?, ?, ?, ?) ");


    sql.append(" insert into review (rnum, rcontent, risbn, rid) ");
    sql.append(" values (review_rnum_seq.nextval, ?, ?, ?) ");


    KeyHolder keyHolder = new GeneratedKeyHolder();
    jdbcTemplate.update(new PreparedStatementCreator() {
      @Override
      public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
        PreparedStatement pstmt = con.prepareStatement(
            sql.toString(),
            new String[]{"rnum"}
        );

//        pstmt.setString(1, reviewDTO.getRcontent());
//        pstmt.setLong(2, reviewDTO.getrstar());
//        pstmt.setLong(3, reviewDTO.getRisbn());
//        pstmt.setString(4, reviewDTO.getRid());


        pstmt.setString(1, reviewDTO.getRcontent());
        pstmt.setLong(2, reviewDTO.getRisbn());
        pstmt.setString(3, reviewDTO.getRid());


        return pstmt;
      }
    }, keyHolder);

    Long rnum = Long.valueOf(keyHolder.getKeys().get("rnum").toString());
    return reSelectOne(rnum);
  }

  @Override
  public ReviewDTO reUpdate(ReviewDTO reviewDTO) {
    StringBuffer sql = new StringBuffer();
    sql.append(" update review ");
    sql.append(" set rcontent = ? , ");
//    sql.append("     rstar = ? , ");
    sql.append("     rudate = systimestamp ");
    sql.append(" where rnum = ? and rid = ? ");

    KeyHolder keyHolder = new GeneratedKeyHolder();
    jdbcTemplate.update(new PreparedStatementCreator() {
      @Override
      public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
        PreparedStatement pstmt = con.prepareStatement(
            sql.toString(),
            new String[]{"rnum"}
        );

//        pstmt.setString(1, reviewDTO.getRcontent());
//        pstmt.setLong(2, reviewDTO.getrstar());
//        pstmt.setLong(3, reviewDTO.getRnum());
//        pstmt.setString(4, reviewDTO.getRid());


        pstmt.setString(1, reviewDTO.getRcontent());
        pstmt.setLong(2, reviewDTO.getRnum());
        pstmt.setString(3, reviewDTO.getRid());






        return pstmt;
      }
    }, keyHolder);

    Long rnum = Long.valueOf(keyHolder.getKeys().get("rnum").toString());
    return reSelectOne(rnum);
  }

  @Override
  public int reDelete(Long rnum, String rid) {
    StringBuffer sql = new StringBuffer();
    sql.append(" delete from review ");
    sql.append("  where rnum =? and rid = ? ");

    int result = jdbcTemplate.update(sql.toString(), rnum, rid);

    return result;
  }
}
