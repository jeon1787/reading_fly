package com.kh.reading_fly.domain.notice.svc;

import com.kh.reading_fly.domain.board.dto.BoardDTO;
import com.kh.reading_fly.domain.common.uploadFile.svc.UploadFileSVC;
import com.kh.reading_fly.domain.notice.dao.NoticeFilterCondition;
import com.kh.reading_fly.domain.notice.dto.NoticeDTO;
import com.kh.reading_fly.domain.notice.dao.NoticeDAO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class NoticeSVCImpl implements NoticeSVC{

  private final NoticeDAO noticeDAO;
  private final UploadFileSVC uploadFileSVC;

  /**
   * 등록
   * @param notice
   * @return
   */
  @Override
  public Long write(NoticeDTO notice) {

    return noticeDAO.create(notice);
  }

  /**
   * 등록 - 파일첨부시
   * @param notice
   * @param files
   * @return
   */

  @Override
  public Long write(NoticeDTO notice, List<MultipartFile> files) {
    //1)원글 저장
    Long rnum = write(notice);

    //2)첨부 저장
    uploadFileSVC.addFile("C0102",rnum,files);

    return rnum;
  }

  /**
   * 전체조회
   * @return
   */
  @Override
  public List<NoticeDTO> findAll() {
    return noticeDAO.findAll();
  }

  @Override
  public List<NoticeDTO> findAll(int startRec, int endRec) {
    return noticeDAO.findAll(startRec,endRec);
  }

  @Override
  public List<NoticeDTO> findAll(NoticeFilterCondition filterCondition) {
    return noticeDAO.findAll(filterCondition);
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
   * 수정 - 파일첨부시
   * @param notice
   * @param files
   * @return
   */

  @Override
  public NoticeDTO modify(NoticeDTO notice, List<MultipartFile> files) {
    //1) 게시글 수정
    NoticeDTO modifiedNotice = noticeDAO.update(notice);
    Long rnum = modifiedNotice.getNNum();

    //2) 첨부파일 저장
    uploadFileSVC.addFile("C0102", rnum, files);

    return modifiedNotice;
  }

  /**
   * 삭제
   * @param nNum
   * @return
   */
  @Override
  public int remove(Long nNum) {

    //1) 첨부파일 삭제
    uploadFileSVC.deleteFileByCodeWithRnum("C0102", nNum);

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

  //전체건수
  @Override
  public int totalCount() {
    return noticeDAO.totalCount();
  }

  @Override
  public int totalCount(NoticeFilterCondition filterCondition) {
    return noticeDAO.totalCount(filterCondition);
  }
}
