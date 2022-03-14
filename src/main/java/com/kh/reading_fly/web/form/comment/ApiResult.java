package com.kh.reading_fly.web.form.comment;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ApiResult<T> {
  private String rtcd;
  private String rtmsg;
  private T data;
}
