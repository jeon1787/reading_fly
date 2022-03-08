package com.kh.reading_fly.web.form.member;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.sql.Date;

@Data
public class EditForm {

  private String id;

  @NotEmpty(message = "비밀번호를 입력하세요")
  @Pattern(regexp = "^.*(?=^.{8,}$)(?=.*\\d)(?=.*[a-zA-Z])(?=.*[!@#$%^&+=]).*$", message="숫자, 문자, 특수문자 포함 8~15자리 이내")
  private String pw;

  @NotEmpty(message = "비밀번호 확인을 입력하세요")
  @Pattern(regexp = "^.*(?=^.{8,}$)(?=.*\\d)(?=.*[a-zA-Z])(?=.*[!@#$%^&+=]).*$", message="숫자, 문자, 특수문자 포함 8~15자리 이내")
  private String pwChk;

  private String name;

  @NotEmpty(message = "닉네임을 입력하세요")
  private String nickname;

  @NotEmpty(message = "Email을 입력하세요")
  @Email
  private String email;
}
