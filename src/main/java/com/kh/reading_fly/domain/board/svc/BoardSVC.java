package com.kh.reading_fly.domain.board.svc;

import com.kh.reading_fly.domain.board.dto.BoardDTO;

import java.util.List;

public interface BoardSVC {
  //전체조회
  List<BoardDTO> findAll();
  //상세조회
  BoardDTO findByBnum(Long bnum);
  //등록
  BoardDTO write(BoardDTO board);
  //수정
  BoardDTO modify(BoardDTO board);
  //삭제
  int remove(Long bnum, String bid);
  //조회수 증가
  int increaseHit(Long bnum);
}
