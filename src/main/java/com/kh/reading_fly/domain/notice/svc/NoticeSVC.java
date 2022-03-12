package com.kh.reading_fly.domain.notice.svc;

import com.kh.reading_fly.domain.notice.dto.NoticeDTO;

import java.util.List;

public interface NoticeSVC {
  //등록
  NoticeDTO write(NoticeDTO notice);
  //전체조회
  List<NoticeDTO> findAll();
  //상세
  NoticeDTO findByNoticeId(Long nNum);
  //수정
  NoticeDTO modify(NoticeDTO notice);
  //삭제
  int remove(Long nNum);
  //조회수증가
  int increaseHit(Long nNum);
}