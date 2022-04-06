package com.kh.reading_fly.domain.qna.dto;

import com.kh.reading_fly.domain.qna.QnaStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class QnaDTO {
  private Long qNum;             //게시글 번호
  private String qTitle;         //제목
  private String qNickname;      //별칭
  private int qHit;              //조회수
  private String qContent;       //내용
  private Long pQNum;            //부모 게시글 번호
  private Long qGroup;           //답글 그룹
  private int qStep;             //답글 단계
  private int qIndent;           //답글 들여쓰기
  private String qStatus;     //게시글 상태(Q:원글, A:답글)
  private LocalDateTime qCDate;  //생성일
  private LocalDateTime qUDate;  //수정일

}
