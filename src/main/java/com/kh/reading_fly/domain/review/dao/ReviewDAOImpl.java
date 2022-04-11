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

  /**
   * 전체조회 by ISBN
   * @param risbn 도서번호
   * @return
   */
  @Override
  public List<ReviewDTO> selectAll(String risbn) {
    StringBuffer sql = new StringBuffer();
    sql.append(" SELECT ");
    sql.append("   row_number() over (order by rcdate) as num, ");
    sql.append("   rnum, ");
    sql.append("   risbn, ");
    sql.append("   rcontent, ");
    sql.append("   rstar, ");
    sql.append("   rcdate, ");
    sql.append("   rudate, ");
    sql.append("   rid, ");
    sql.append("   nickname ");
    sql.append(" FROM ");
    sql.append("   review, ");
    sql.append("   member ");
    sql.append(" WHERE ");
    sql.append("   review.rid = member.id ");
    sql.append(" AND risbn = ? ");
    sql.append(" ORDER BY rcdate ASC ");

    //sql 실행
    List<ReviewDTO> list = jdbcTemplate.query(sql.toString(), new BeanPropertyRowMapper<>(ReviewDTO.class), risbn);

    return list;
  }

  /**
   * 단건조회 by rnum
   * @param rnum 리뷰번호
   * @return
   */
  @Override
  public ReviewDTO selectOne(Long rnum) {
    StringBuffer sql = new StringBuffer();

    sql.append(" SELECT ");
    sql.append("   rnum, ");
    sql.append("   risbn, ");
    sql.append("   rcontent, ");
    sql.append("   rstar, ");
    sql.append("   rcdate, ");
    sql.append("   rudate, ");
    sql.append("   rid, ");
    sql.append("   nickname ");
    sql.append(" FROM ");
    sql.append("   review, ");
    sql.append("   member ");
    sql.append(" WHERE ");
    sql.append("   review.rid = member.id ");
    sql.append(" AND ");
    sql.append("   rnum = ? ");

    //sql 실행
    List<ReviewDTO> query = jdbcTemplate.query(sql.toString(),
                            new BeanPropertyRowMapper<>(ReviewDTO.class), rnum);
    return (query.size() == 1)? query.get(0) : null;
  }

  /**
   * 리뷰 등록
   * @param reviewDTO
   * @return
   */
  @Override
  public ReviewDTO create(ReviewDTO reviewDTO) {
    StringBuffer sql = new StringBuffer();
    sql.append(" INSERT INTO ");
    sql.append("   review (rnum, rcontent, risbn, rid) ");
    sql.append(" VALUES ");
    sql.append("   (review_rnum_seq.nextval, ?, ?, ?) ");


    KeyHolder keyHolder = new GeneratedKeyHolder();
    jdbcTemplate.update(new PreparedStatementCreator() {
      @Override
      public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
        PreparedStatement pstmt = con.prepareStatement(
            sql.toString(),
            new String[]{"rnum"}
        );

        pstmt.setString(1, reviewDTO.getRcontent());
        pstmt.setString(2, reviewDTO.getRisbn());
        pstmt.setString(3, reviewDTO.getRid());

        return pstmt;
      }
    }, keyHolder);

    Long rnum = Long.valueOf(keyHolder.getKeys().get("rnum").toString());
    return selectOne(rnum);
  }

  /**
   * 리뷰 수정
   * @param reviewDTO
   * @return
   */
  @Override
  public ReviewDTO update(ReviewDTO reviewDTO) {
    StringBuffer sql = new StringBuffer();
    sql.append(" UPDATE ");
    sql.append("   review ");
    sql.append(" SET ");
    sql.append("   rcontent = ? , ");
    sql.append("   rstar = ? , ");
    sql.append("   rudate = systimestamp ");
    sql.append(" WHERE ");
    sql.append("   rnum = ? ");
    sql.append(" AND ");
    sql.append("   rid = ? ");

    KeyHolder keyHolder = new GeneratedKeyHolder();
    jdbcTemplate.update(new PreparedStatementCreator() {
      @Override
      public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
        PreparedStatement pstmt = con.prepareStatement(
            sql.toString(),
            new String[]{"rnum"}
        );

        pstmt.setString(1, reviewDTO.getRcontent());
        pstmt.setLong(2, reviewDTO.getRnum());
        pstmt.setString(3, reviewDTO.getRid());

        return pstmt;
      }
    }, keyHolder);

    Long rnum = Long.valueOf(keyHolder.getKeys().get("rnum").toString());
    return selectOne(rnum);
  }

  /**
   * 리뷰 삭제
   * @param rnum
   * @param rid
   * @return
   */
  @Override
  public int delete(Long rnum, String rid) {
    StringBuffer sql = new StringBuffer();
    sql.append(" DELETE FROM ");
    sql.append("   review ");
    sql.append("  WHERE ");
    sql.append("    rnum =? ");
    sql.append("  AND ");
    sql.append("    rid = ? ");

    int result = jdbcTemplate.update(sql.toString(), rnum, rid);

    return result;
  }
}
