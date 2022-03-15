package com.kh.reading_fly.domain.comment.dao;

import com.kh.reading_fly.domain.comment.dto.CommentDTO;

import java.util.List;

public interface CommentDAO {

  /**
   * 게시글번호로 댓글조회
   * @param cbnum 게시글번호
   * @return 댓글
   */
  List<CommentDTO> selectAll(Long cbnum);

  /**
   * 댓글등록
   * @param comment 댓글
   * @return 댓글
   */
  CommentDTO create(CommentDTO comment);
}
