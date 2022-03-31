package com.kh.reading_fly.domain.qna.svc;

import com.kh.reading_fly.domain.qna.dto.QnaDTO;

import java.util.List;

public interface QnaSVC {
  /**
   * 원글작성
   *
   * @param qna
   * @return 게시글 번호
   */
  Long saveOrigin(QnaDTO qna);

  /**
   * 목록
   *
   * @return
   */
  List<QnaDTO> findAll();
  List<QnaDTO> findAll(int startRec, int endRec);

  /**
   * 상세 조회
   *
   * @param qNum 게시글번호
   * @return
   */
  QnaDTO findByQNum(Long qNum);

  /**
   * 삭제
   *
   * @param qNum 게시글번호
   * @return 삭제건수
   */
  int deleteByQNum(Long qNum);

  /**
   * 수정
   *
   * @param qNum  게시글 번호
   * @param qna 수정내용
   * @return 수정건수
   */
  int updateByQNum(Long qNum, QnaDTO qna);

  /**
   * 답글작성
   *
   * @param pQNum   부모글번호
   * @param replyQna 답글
   * @return 답글번호
   */
  Long saveReply(Long pQNum, QnaDTO replyQna);

  /**
   * 전체건수
   *
   * @return 게시글 전체건수
   */
  int totalCount();
}
