package com.kh.reading_fly.domain.board.svc;

import com.kh.reading_fly.domain.board.dao.BoardDAO;
import com.kh.reading_fly.domain.board.dao.BoardFilterCondition;
import com.kh.reading_fly.domain.board.dto.BoardDTO;
import com.kh.reading_fly.domain.comment.dao.CommentDAO;
import com.kh.reading_fly.domain.common.uploadFile.svc.UploadFileSVC;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class BoardSVCImpl implements BoardSVC{

  private final BoardDAO boardDAO;
  private final UploadFileSVC uploadFileSVC;

  /**
   * 전체조회
   * @return 게시글 List
   */
  @Override
  public List<BoardDTO> findAll() {
    return boardDAO.selectAll();
  }

  /**
   * 전체조회 - 페이징
   * @param startRec
   * @param endRec
   * @return
   */
  @Override
  public List<BoardDTO> findAll(int startRec, int endRec) {
    return boardDAO.selectAll(startRec, endRec);
  }

  /**
   * 전체조회 - 검색
   * @param filterCondition 시작레코드번호, 종료레코드번호, 검색유형, 검색어
   * @return
   */
  @Override
  public List<BoardDTO> findAll(BoardFilterCondition filterCondition) {
    return boardDAO.findAll(filterCondition);
  }

  /**
   * 상세조회
   * @param bnum
   * @return 게시글 BoardDTO
   */
  @Override
  public BoardDTO findByBnum(Long bnum) {
    //1) 상세조회한 DTO
    BoardDTO boardDTO = boardDAO.selectOne(bnum);

    //2) 조회수 증가
    boardDAO.updateHit(bnum);

    return boardDTO;
  }

  /**
   * 등록
   * @param board
   * @return
   */
  @Override
  public BoardDTO write(BoardDTO board) {
    return boardDAO.create(board);
  }

  /**
   * 등록 - 파일첨부시
   * @param board
   * @param files
   * @return
   */
  @Override
  public BoardDTO write(BoardDTO board, List<MultipartFile> files) {
    //1) 게시글 저장
    BoardDTO savedBoard = boardDAO.create(board);
    Long rnum = savedBoard.getBnum();

    //2) 첨부파일 저장
    uploadFileSVC.addFile("B", rnum, files);

    return savedBoard;
  }

  /**
   * 수정
   * @param board
   * @return
   */
  @Override
  public BoardDTO modify(BoardDTO board) {
    return boardDAO.update(board);
  }

  /**
   * 수정 - 파일첨부시
   * @param board
   * @param files
   * @return
   */
  @Override
  public BoardDTO modify(BoardDTO board, List<MultipartFile> files) {
    //1) 게시글 수정
    BoardDTO modifiedBoard = boardDAO.update(board);
    Long rnum = modifiedBoard.getBnum();

    //2) 첨부파일 저장
    uploadFileSVC.addFile("B", rnum, files);

    return modifiedBoard;
  }

  /**
   * 댓글 없는 게시글 삭제
   * @param bnum
   * @return
   */
  @Override
  public int removeBoard(Long bnum, String bid) {
    //1) 첨부파일 삭제
    uploadFileSVC.deleteFileByCodeWithRnum("B", bnum);

    //2) 게시글 삭제
    return boardDAO.deleteBoard(bnum, bid);
  }

  /**
   * 댓글 있는 게시글 삭제
   * @param bnum
   * @return
   */
  @Override
  public int removeContentOfBoard(Long bnum, String bid) {
    //1) 첨부파일 삭제
    uploadFileSVC.deleteFileByCodeWithRnum("B", bnum);

    //2) 게시글 삭제
    return boardDAO.deleteContentOfBoard(bnum, bid);
  }

  /**
   * 조회수 증가
   * @param bnum
   * @return
   */
  @Override
  public int increaseHit(Long bnum) {
    return boardDAO.updateHit(bnum);
  }

  /**
   * 전체건수
   * @return 게시글 전체건수
   */
  @Override
  public int totalCount() {
    return boardDAO.totalCount();
  }

  /**
   * 전체건수 - 검색
   * @param filterCondition 시작레코드번호, 종료레코드번호, 검색유형, 검색어
   * @return
   */
  @Override
  public int totalCount(BoardFilterCondition filterCondition) {
    return boardDAO.totalCount(filterCondition);
  }
}
