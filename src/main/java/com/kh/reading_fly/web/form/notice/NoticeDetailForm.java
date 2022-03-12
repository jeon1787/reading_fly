package com.kh.reading_fly.web.form.notice;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class NoticeDetailForm {
  private Long nNum;
  private String nTitle;
  private String nContent;
}
