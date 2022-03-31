package com.kh.reading_fly.domain.notice.dao;

import com.kh.reading_fly.domain.notice.dto.NoticeDTO;

import java.util.List;

public interface NoticeDAO {
  //등록
  Long create(NoticeDTO notice);

  //전체조회
  List<NoticeDTO> findAll();
  List<NoticeDTO> findAll(int startRec, int endRec);

  //상세
  NoticeDTO selectOne(Long nNum);

  //수정
  NoticeDTO update(NoticeDTO notice);

  //삭제
  int delete(Long nNum);

  //조회수증가
  int updateHit(Long nNum);

  //게시글 전체건수
  int totalCount();
}
