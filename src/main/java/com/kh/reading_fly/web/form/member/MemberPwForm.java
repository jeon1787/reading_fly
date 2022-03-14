package com.kh.reading_fly.web.form.member;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Data
public class MemberPwForm {

  @NotEmpty(message = "비밀번호를 입력하세요")
  @Pattern(regexp = "^.*(?=^.{8,15}$)(?=.*\\d)(?=.*[a-zA-Z])(?=.*[!@#$%^&+=]).*$", message="숫자, 문자, 특수문자 포함 8~15자리 이내")
  private String pw;
}

