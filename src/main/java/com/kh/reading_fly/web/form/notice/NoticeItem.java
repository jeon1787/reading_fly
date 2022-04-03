package com.kh.reading_fly.web.form.notice;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NoticeItem {
  private Long nNum;
  private String nTitle;
  private String nCDate;
  private String nUDate;
  private Long nHit;
}
