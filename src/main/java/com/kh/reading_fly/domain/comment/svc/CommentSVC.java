package com.kh.reading_fly.domain.comment.svc;

import com.kh.reading_fly.domain.comment.dto.CommentDTO;

import java.util.List;

public interface CommentSVC {

  List<CommentDTO> findAll(Long cbnum);

  CommentDTO findByCnum(Long cnum);

  CommentDTO write(CommentDTO comment);

  CommentDTO modify(CommentDTO comment);
}
