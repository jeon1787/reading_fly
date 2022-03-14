package com.kh.reading_fly.web.form.comment;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter@Setter
@ToString
public class ItemForm {
  private Long cnum;            //댓글번호
  private LocalDateTime cudate; //날짜
  private String ccontent;      //댓글본문
  private String nickname;      //닉네임
}
