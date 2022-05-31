package com.kh.reading_fly.domain.board.dao;

import com.kh.reading_fly.domain.board.dto.BoardDTO;
import com.kh.reading_fly.domain.board.svc.BoardSVC;
import com.kh.reading_fly.domain.common.paging.FindCriteria;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@Slf4j
@SpringBootTest
public class BoardDAOImplTest {

  @Autowired
  private BoardDAO boardDAO;
  @Autowired
  private BoardSVC boardSVC;
  @Autowired
  @Qualifier("fc10")
  private FindCriteria fc;

  @DisplayName("다수의 원글 작성")
  @Test
  void saveOrigins() {

    for(int i=1; i<=244; i++) {
      BoardDTO board = new BoardDTO();

      board.setBtitle("게시글제목"+i);
      board.setBid("user1");
      board.setBcontent("게시글본문"+i);

      BoardDTO saveOriginId = boardDAO.create(board);
    }
  }

  @DisplayName("게시글 전체조회")
  @Test
  void selectAll(){
    List<BoardDTO> list = boardDAO.selectAll(1, 10);
    Assertions.assertThat(list.size()).isEqualTo(10);

    for (BoardDTO boardDTO : list) {
      log.info(boardDTO.toString());
    }
  }

  @DisplayName("게시글 검색")
  @Test
  void findAll(){
    String searchType = "C";
    String keyword = "내용";

    BoardFilterCondition filterCondition = new BoardFilterCondition(
            fc.getRc().getStartRec(), fc.getEndPage(),
            searchType,
            keyword
    );
    fc.setTotalRec(boardSVC.totalCount(filterCondition));
    fc.setSearchType(searchType);
    fc.setKeyword(keyword);
    List<BoardDTO> list = boardDAO.findAll(filterCondition);

    for (BoardDTO boardDTO : list) {
      log.info(boardDTO.toString());
    }
  }

  @DisplayName("게시글 상세조회")
  @Test
  void selectOne(){

  }

  @DisplayName("게시글 등록")
  @Test
  void create(){

  }


//  @Test
//  @DisplayName("게시글 삭제")//Test 성공
//  void delete(){
//    //when
//    Long bnum = 2L;
//    String bid = "user1";
//
//    //try
//    int resultNum = boardDAO.deleteContentOfBoard(bnum, bid);
//
//    //then
////    BoardDTO result = boardDAO.selectOne(bnum);//실패이유 selectOne 은 bstatus 가져오지 않음
////    log.info("status={}",result.getBstatus());
////    Assertions.assertThat(result.getBstatus()).isEqualTo("D");
//    Assertions.assertThat(resultNum).isEqualTo(1);//성공시 1, 실패시 0
//  }
}
