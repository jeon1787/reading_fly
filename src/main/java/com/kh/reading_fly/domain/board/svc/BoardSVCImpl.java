package com.kh.reading_fly.domain.board.svc;

import com.kh.reading_fly.domain.board.dao.BoardDAO;
import com.kh.reading_fly.domain.board.dto.BoardDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    //상세조회한 DTO
    BoardDTO boardDTO = boardDAO.selectOne(bnum);

    //조회수 증가
    boardDAO.updateHit(bnum);

    return boardDTO;
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
