package com.kh.reading_fly.domain.board.dao;

import com.kh.reading_fly.domain.board.dto.BoardDTO;

import java.util.List;

public interface BoardDAO {
  //전체조회
  List<BoardDTO> selectAll();
  //상세조회
  BoardDTO selectOne(Long bnum);
  //등록
  BoardDTO create(BoardDTO board);
  //수정
  BoardDTO update(BoardDTO board);
  //댓글 없는 게시글 삭제
  int delete1(Long bnum, String bid);
  //댓글 있는 게시글 삭제
  int delete2(Long bnum, String bid);
  //조회수 증가
  int updateHit(Long bnum);
}
