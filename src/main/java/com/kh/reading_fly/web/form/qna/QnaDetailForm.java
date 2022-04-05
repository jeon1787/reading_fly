package com.kh.reading_fly.web.form.qna;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class QnaDetailForm {
  private Long qNum;           //게시글번호
  private String qTitle;         //제목
  private String qEmail;         //EMAIL
  private String qNickname;      //별칭
  private LocalDateTime qUDate;  //날짜
  private String qContent;      //내용
  private int qHit;              //조회수
  private int qIndent;           //답글 들여쓰기추가
}
