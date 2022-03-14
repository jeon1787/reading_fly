package com.kh.reading_fly.domain.comment.dao;

import com.kh.reading_fly.domain.comment.dto.CommentDTO;
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
public class CommentDAOImpl implements CommentDAO{

  private final JdbcTemplate jdbcTemplate;

  /**
   * 게시글번호로 댓글조회
   * @param cbnum 게시글번호
   * @return 댓글
   */
  @Override
  public List<CommentDTO> selectAll(Long cbnum) {

    //sql 작성
    StringBuffer sql = new StringBuffer();
    sql.append(" select row_number() over (order by ccdate) as num, cnum, cbnum, ccontent, ccdate, cudate, cid, nickname ");
    sql.append(" from comments, member ");
    sql.append(" where comments.cid = member.id and cbnum = ? ");
    sql.append(" order by ccdate asc ");

    //sql 실행
    List<CommentDTO> list = jdbcTemplate.query(sql.toString(), new BeanPropertyRowMapper<>(CommentDTO.class), cbnum);

    return list;
  }

  /**
   * 댓글등록
   * @param comment 댓글
   * @return 댓글
   */
  @Override
  public CommentDTO create(CommentDTO comment) {
    //sql 작성
    StringBuffer sql = new StringBuffer();
    sql.append(" insert into comments (cnum, ccontent, cid, cbnum) ");
    sql.append(" values (board_bnum_seq.nextval, ?, ?, ?) ");

    //sql 실행
    List<CommentDTO> query = jdbcTemplate.query(
            sql.toString(),
            new BeanPropertyRowMapper<>(CommentDTO.class),
            comment.getCcontent(),
            comment.getCid(),
            comment.getCbnum());

    return (query.size() == 1)? query.get(0) : null;
  }
}
