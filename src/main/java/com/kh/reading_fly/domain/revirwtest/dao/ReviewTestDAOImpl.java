package com.kh.reading_fly.domain.revirwtest.dao;

import com.kh.reading_fly.domain.revirwtest.dto.ReviewReqDTO;
import com.kh.reading_fly.domain.revirwtest.dto.ReviewTestDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Repository
public class ReviewTestDAOImpl implements ReviewTestDAO{

  private final JdbcTemplate jdbcTemplate;



  @Override
  public int registReview(ReviewTestDTO reviewTestDTO) {
    StringBuffer sql = new StringBuffer();
    sql.append(" insert into review (rnum, rcontent, rstar, risbn, rid) ");
    sql.append(" values (review_rnum_seq.nextval, ?, ?, ?, ?) ");

    jdbcTemplate.update(sql.toString(),
        reviewTestDTO.getRcontent(), reviewTestDTO.getRstar(),
        reviewTestDTO.getRisbn(), reviewTestDTO.getRid()
    );

    return rnumCurrVal();
  }

  @Override
  public List<ReviewReqDTO> allReview(Long risbn) {
    StringBuffer sql = new StringBuffer();
//    sql.append(" select row_number() over (order by rcdate) as num, rnum, risbn, rcontent, rstar, rcdate, rudate, rid, nickname ");
    sql.append(" select rnum, risbn, rcontent, rstar, rcdate, rudate, rid, nickname ");
    sql.append(" from review, member ");
    sql.append(" where review.rid = member.id and risbn = ? ");
    sql.append(" order by rcdate asc ");

    List<ReviewReqDTO> list = jdbcTemplate.query(sql.toString(),
        new BeanPropertyRowMapper<>(ReviewReqDTO.class),
        risbn);

    return list;
  }

  @Override
  public ReviewReqDTO findReview(int rnum) {
    StringBuffer sql = new StringBuffer();
    sql.append(" select rnum, risbn, rcontent, rstar,rcdate, rudate, rid, nickname ");
    sql.append(" from review, member ");
    sql.append(" where review.rid = member.id and rnum = ? ");

    ReviewReqDTO reviewReqDTO = 	jdbcTemplate.queryForObject(sql.toString(),
                                  new BeanPropertyRowMapper<>(ReviewReqDTO.class),rnum);
    return reviewReqDTO;
  }

  @Override
  public void updateReview(ReviewTestDTO reviewTestDTO) {
    StringBuffer sql = new StringBuffer();
    sql.append(" update review ");
    sql.append(" set rcontent = ? , ");
//    sql.append("     rstar = ? , ");
    sql.append("     rudate = systimestamp ");
    sql.append(" where rnum = ? and rid = ? ");

    jdbcTemplate.update(sql.toString(), reviewTestDTO.getRcontent(),
                          reviewTestDTO.getRstar(), reviewTestDTO.getRnum(), reviewTestDTO.getRid()
    );

  }

  @Override
  public void removeReview(int rnum) {
    StringBuffer sql = new StringBuffer();
    sql.append("delete from review ");
    sql.append(" where rnum = ? ");

    jdbcTemplate.update(sql.toString(), rnum);

  }

  @Override
  public Integer rnumCurrVal() {
    String sql = "select review_rnum_seq.currval from dual ";
    int rnum = jdbcTemplate.queryForObject(sql,Integer.class);
    return rnum;
  }


}
