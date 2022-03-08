package com.kh.reading_fly.web.form.member;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class OutForm {

  private String id;


  @NotBlank
  private String pw;

  private Boolean agree;
}