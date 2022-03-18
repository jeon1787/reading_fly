package com.kh.reading_fly.web.form.member;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Data
public class OutForm {

  private String id;

  private String name;

  private String nickname;

  private String email;

  @NotBlank
  private String pw;

  private Boolean agree;


}