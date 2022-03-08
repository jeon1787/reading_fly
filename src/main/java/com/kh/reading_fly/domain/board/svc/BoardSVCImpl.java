package com.kh.reading_fly.domain.board.svc;

import com.kh.reading_fly.domain.board.dao.BoardDAO;
import com.kh.reading_fly.domain.board.dto.BoardDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BoardSVCImpl implements BoardSVC{

  private final BoardDAO boardDAO;

  /**
   * 전체조회
   * @return 게시글 List
   */
  @Override
  public List<BoardDTO> findAll() {
    return boardDAO.selectAll();
  }

  /**
   * 상세조회
   * @param bnum
   * @return 게시글 BoardDTO
   */
  @Override
  public BoardDTO findByBnum(Long bnum) {
    return boardDAO.selectOne(bnum);
  }

  /**
   * 등록
   * @param board
   * @return
   */
  @Override
  public BoardDTO write(BoardDTO board) {
    return boardDAO.create(board);
  }

  /**
   * 수정
   * @param board
   * @return
   */
  @Override
  public BoardDTO modify(BoardDTO board) {
    return boardDAO.update(board);
  }

  /**
   * 삭제
   * @param bnum
   * @return
   */
  @Override
  public int remove(Long bnum, String bid) {
    return boardDAO.delete(bnum, bid);
  }

  /**
   * 조회수 증가
   * @param bnum
   * @return
   */
  @Override
  public int increaseHit(Long bnum) {
    return boardDAO.updateHit(bnum);
  }
}
