package com.kh.reading_fly.domain.board.svc;

import com.kh.reading_fly.domain.board.dto.BoardDTO;

import java.util.List;

public interface BoardSVC {
  /**
   * 전체조회
   * @return
   */
  List<BoardDTO> findAll();

  /**
   * 전체조회(페이징)
   * @param startRec
   * @param endRec
   * @return
   */
  List<BoardDTO>  findAll(int startRec, int endRec);

  /**
   * 상세조회
   * @param bnum
   * @return
   */
  BoardDTO findByBnum(Long bnum);

  /**
   * 등록
   * @param board
   * @return
   */
  BoardDTO write(BoardDTO board);

  /**
   * 수정
   * @param board
   * @return
   */
  BoardDTO modify(BoardDTO board);

  /**
   * 댓글 없는 게시글 삭제
   * @param bnum
   * @param bid
   * @return
   */
  int removeBoard(Long bnum, String bid);

  /**
   * 댓글 있는 게시글 삭제
   * @param bnum
   * @param bid
   * @return
   */
  int removeContentOfBoard(Long bnum, String bid);

  /**
   * 조회수 증가
   * @param bnum
   * @return
   */
  int increaseHit(Long bnum);

  /**
   * 전체건수
   * @return 게시글 전체건수
   */
  int totalCount();
}
