package com.kh.reading_fly.web.form.support;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class FindIdReq {


  @NotBlank
  private String name;


  @NotBlank
  @Email
  private String email;

}
