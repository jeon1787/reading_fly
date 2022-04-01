package com.kh.reading_fly.web.form.reviewTest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewTestAPIResult<T> {
  private String rtcd;
  private String rtmsg;
  private T data;
}
