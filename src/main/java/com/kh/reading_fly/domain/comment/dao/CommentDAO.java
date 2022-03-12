package com.kh.reading_fly.domain.comment.dao;

import com.kh.reading_fly.domain.comment.dto.CommentDTO;

import java.util.List;

public interface CommentDAO {
  //게시글번호로 댓글 목록 조회
  List<CommentDTO> selectAll();
}
