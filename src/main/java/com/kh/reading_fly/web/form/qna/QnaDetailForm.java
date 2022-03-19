package com.kh.reading_fly.web.form.qna;

import lombok.Data;

@Data
public class QnaDetailForm {
  private Long qNum;           //게시글번호
  private String qTitle;         //제목
  private String qEmail;         //EMAIL
  private String qNickname;      //별칭
  private String qContent;      //내용
  private int qHit;              //조회수
}
