package com.kh.reading_fly.domain.comment.svc;

import com.kh.reading_fly.domain.comment.dto.CommentDTO;

import java.util.List;

public interface CommentSVC {

  List<CommentDTO> findAll();
}