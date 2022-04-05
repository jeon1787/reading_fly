package com.kh.reading_fly.web.form.notice;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class NoticeDetailForm {
  private Long nNum;             //공지 아이디
  private String nTitle;         //제목
  private String nContent;       //본문
  private Long  nHit;            //조회수
  private String nCDate;  //생성일시
  private String nUDate;  //수정일시

}
