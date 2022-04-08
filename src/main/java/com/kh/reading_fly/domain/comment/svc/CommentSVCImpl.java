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
  public List<CommentDTO> findAll(Long cbnum) {
    return commentDAO.selectAll(cbnum);
  }

  @Override
  public CommentDTO findByCnum(Long cnum) {
    return commentDAO.selectOne(cnum);
  }

  @Override
  public CommentDTO write(CommentDTO comment) {
    return commentDAO.create(comment);
  }

  @Override
  public CommentDTO modify(CommentDTO comment) {
    return commentDAO.update(comment);
  }

  @Override
  public int remove(Long cnum, String cid) {
    return commentDAO.delete(cnum, cid);
  }

  @Override
  public int eachCount(Long cbnum){ return  commentDAO.eachCount(cbnum); }
}
