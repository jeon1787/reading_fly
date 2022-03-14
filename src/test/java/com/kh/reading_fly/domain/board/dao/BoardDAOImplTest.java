package com.kh.reading_fly.domain.board.dao;

import com.kh.reading_fly.domain.board.dto.BoardDTO;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
public class BoardDAOImplTest {

  @Autowired
  private BoardDAO boardDAO;

  @Test
  @DisplayName("게시글 삭제")//Test 성공
  void delete(){
    //when
    Long bnum = 2L;
    String bid = "user1";

    //try
    int resultNum = boardDAO.delete2(bnum, bid);

    //then
//    BoardDTO result = boardDAO.selectOne(bnum);//실패이유 selectOne 은 bstatus 가져오지 않음
//    log.info("status={}",result.getBstatus());
//    Assertions.assertThat(result.getBstatus()).isEqualTo("D");
    Assertions.assertThat(resultNum).isEqualTo(1);//성공시 1, 실패시 0
  }
}
