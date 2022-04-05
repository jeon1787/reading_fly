package com.kh.reading_fly.domain.qna.svc;

import com.kh.reading_fly.domain.common.uploadFile.svc.UploadFileSVC;
import com.kh.reading_fly.domain.qna.dao.QnaDAO;
import com.kh.reading_fly.domain.qna.dao.QnaFilterCondition;
import com.kh.reading_fly.domain.qna.dto.QnaDTO;
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
public class QnaSVCImpl implements QnaSVC {

  private  final QnaDAO qnaDAO;
  private final UploadFileSVC uploadFileSVC;

  //원글
  @Override
  public Long saveOrigin(QnaDTO qna) {
    return qnaDAO.saveOrigin(qna);
  }

  @Override
  public Long saveOrigin(QnaDTO qna, List<MultipartFile> files) {
    //1)원글 저장
    Long qnaId = saveOrigin(qna);

    //2)첨부 저장
    uploadFileSVC.addFile("Q",qnaId,files);

    return qnaId;
  }


  //목록
  @Override
  public List<QnaDTO> findAll() {
    return qnaDAO.findAll();
  }

  @Override
  public List<QnaDTO> findAll(int startRec, int endRec) {
    return qnaDAO.findAll(startRec,endRec);
  }

  @Override
  public List<QnaDTO> findAll(QnaFilterCondition filterCondition) {
    return qnaDAO.findAll(filterCondition);
  }

  //상세조회
  @Override
  public QnaDTO findByQNum(Long qNum) {
    QnaDTO findedItem = qnaDAO.findByQNum(qNum);
    qnaDAO.increaseHitCount(qNum);
    return  findedItem;
  }

  //삭제
  @Override
  public int deleteByQNum(Long qNum) {
    return qnaDAO.deleteByQNum(qNum);
  }

  //수정
  @Override
  public int updateByQNum(Long qNum, QnaDTO qna) {
    return qnaDAO.updateByQNum(qNum, qna);
  }

  //수정-첨부파일
  @Override
  public int updateByBbsId(Long id, QnaDTO qna, List<MultipartFile> files) {
    //1)수정
    int affectedRow = updateByQNum(id,qna);

    //2)첨부 저장
    uploadFileSVC.addFile("Q",id,files);

    return affectedRow;
  }


  //답글
  @Override
  public Long saveReply(Long pQNum, QnaDTO replyQna) {
    return qnaDAO.saveReply(pQNum, replyQna);
  }

  //전체건수
  @Override
  public int totalCount() {
    return qnaDAO.totalCount();
  }

  @Override
  public int totalCount(QnaFilterCondition filterCondition) {
    return qnaDAO.totalCount(filterCondition);
  }
}
