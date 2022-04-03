package com.kh.reading_fly.web.form.reviewTest;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReviewTestForm {

  private Integer rnum;

  private Long risbn;
  private String rcontent;
  private Integer rstar;
  private String rid;

  private LocalDateTime rcdate;
  private LocalDateTime rudate;

  private String nickname;




}
