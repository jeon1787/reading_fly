package com.kh.reading_fly.web.form.review;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ReviewEditForm {

  private Long rnum;
  private String rid;
  private String rcontent;

  private Long rstar;

}
