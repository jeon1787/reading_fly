package com.kh.reading_fly.domain.notice.svc;

import com.kh.reading_fly.domain.board.dto.BoardDTO;
import com.kh.reading_fly.domain.notice.dao.NoticeFilterCondition;
import com.kh.reading_fly.domain.notice.dto.NoticeDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface NoticeSVC {
  //등록
  Long write(NoticeDTO notice);

  /**
   * 원글작성-첨부파일 있는경우
   * @param notice
   * @param files 첨파일
   * @return 게시글 번호
   */
  Long write(NoticeDTO notice, List<MultipartFile> files);

  //전체조회
  List<NoticeDTO> findAll();
  List<NoticeDTO> findAll(int startRec, int endRec);
  /**
   * 검색
   * @param filterCondition 분류,시작레코드번호,종료레코드번호,검색유형,검색어
   * @return
   */
  List<NoticeDTO>  findAll(NoticeFilterCondition filterCondition);
  //상세
  NoticeDTO findByNoticeId(Long nNum);
  //수정
  NoticeDTO modify(NoticeDTO notice);

  /**
   * 수정 - 파일첨부시
   * @param notice
   * @param files
   * @return
   */
  NoticeDTO modify(NoticeDTO notice, List<MultipartFile> files);
  //삭제
  int remove(Long nNum);
  //조회수증가
  int increaseHit(Long nNum);

  //게시글 전체 건수
  int totalCount();
  int totalCount(NoticeFilterCondition filterCondition);
}
