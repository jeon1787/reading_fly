package com.kh.reading_fly.web.form.board;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DetailForm {
  private Long num;
  private String title;
  private String date;
  private String content;
  private Long hit;
  private String id;
  private String nickname;
}
