package com.kh.reading_fly.domain.comment.svc;

import com.kh.reading_fly.domain.comment.dao.CommentDAO;
import com.kh.reading_fly.domain.comment.dto.CommentDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentSVCImpl implements CommentSVC{

  private final CommentDAO commentDAO;

  @Override
  public List<CommentDTO> findAll() {
    return commentDAO.selectAll();
  }
}
