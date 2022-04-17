package com.kh.reading_fly.web.form.review;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ReviewItemForm {//리뷰 목록용

  private Long rnum;
  private String rid;
  private String nickname;
  private String rcontent;
  private String rudate;

  private Long rstar;

}
