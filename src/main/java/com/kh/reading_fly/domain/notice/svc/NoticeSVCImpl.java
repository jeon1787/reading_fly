package com.kh.reading_fly.domain.notice.svc;

import com.kh.reading_fly.domain.notice.dto.NoticeDTO;
import com.kh.reading_fly.domain.notice.dao.NoticeDAO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class NoticeSVCImpl implements NoticeSVC{

  private final NoticeDAO noticeDAO;

  /**
   * 등록
   * @param notice
   * @return
   */
  @Override
  public NoticeDTO write(NoticeDTO notice) {

    return noticeDAO.create(notice);
  }

  /**
   * 전체조회
   * @return
   */
  @Override
  public List<NoticeDTO> findAll() {
    return noticeDAO.selectAll();
  }

  /**
   * 상세조회
   * @param nNum 공지사항 번호
   * @return 공지사항 상세
   */
  @Override
  public NoticeDTO findByNoticeId(Long nNum) {
    NoticeDTO notice = noticeDAO.selectOne(nNum);
    noticeDAO.updateHit(nNum);
    return notice;
  }

  /**
   * 수정
   * @param notice
   * @return
   */
  @Override
  public NoticeDTO modify(NoticeDTO notice) {
    return noticeDAO.update(notice);
  }

  /**
   * 삭제
   * @param nNum
   * @return
   */
  @Override
  public int remove(Long nNum) {
    return noticeDAO.delete(nNum);
  }

  /**
   * 조회수 증가
   * @param nNum
   * @return
   */
  @Override
  public int increaseHit(Long nNum) {
    return noticeDAO.updateHit(nNum);
  }
}
