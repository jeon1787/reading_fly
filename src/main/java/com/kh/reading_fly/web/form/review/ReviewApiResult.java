package com.kh.reading_fly.web.form.review;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ReviewApiResult<T> {

  private String rtcd;
  private String rtmsg;
  private T data;

}
