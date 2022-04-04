package com.kh.reading_fly.domain.qna.svc;

import com.kh.reading_fly.domain.qna.dao.QnaFilterCondition;
import com.kh.reading_fly.domain.qna.dto.QnaDTO;
import org.springframework.web.multipart.MultipartFile;

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
   * 원글작성-첨부파일 있는경우
   * @param qna
   * @param files 첨파일
   * @return 게시글 번호
   */
  Long saveOrigin(QnaDTO qna, List<MultipartFile> files);

  /**
   * 목록
   *
   * @return
   */
  List<QnaDTO> findAll();
  List<QnaDTO> findAll(int startRec, int endRec);
  /**
   * 검색
   * @param filterCondition 분류,시작레코드번호,종료레코드번호,검색유형,검색어
   * @return
   */
  List<QnaDTO>  findAll(QnaFilterCondition filterCondition);

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
   * 수정-첨부
   * @param id 게시글 번호
   * @param qna 수정내용
   * @param files 첨부파일
   * @return 수정건수
   */
  int updateByBbsId(Long id,QnaDTO qna, List<MultipartFile> files);

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
  int totalCount(QnaFilterCondition filterCondition);
}
