package com.kh.reading_fly.domain.comment.dao;

import com.kh.reading_fly.domain.comment.dto.CommentDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
public class CommentDAOImpl implements CommentDAO{

  private final JdbcTemplate jdbcTemplate;

  /**
   * 전체조회
   * @return
   */
  @Override
  public List<CommentDTO> selectAll() {

    //sql 작성
    StringBuffer sql = new StringBuffer();
    sql.append(" select row_number() over (order by ccdate) as num, cnum, ccontent, ccdate, cudate, cid, nickname ");
    sql.append(" from comments, member ");
    sql.append(" where comments.cid = member.id ");
    sql.append(" order by ccdate desc ");

    //sql 실행
    List<CommentDTO> list = jdbcTemplate.query(sql.toString(), new BeanPropertyRowMapper<>(CommentDTO.class));

    return null;
  }
}
