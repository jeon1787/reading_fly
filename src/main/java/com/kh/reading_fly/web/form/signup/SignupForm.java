package com.kh.reading_fly.web.form.singup;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class SignupForm {

  @NotBlank
  @Size(min=4, max=8)
//  @Pattern(regexp = "^[A-Za-z0-9]{4,8}*$", message = "영문 대문자 또는 소문자 또는 숫자 포함 4~8 자리 이내")
  private String id;

  @NotBlank
  @Size(min=8, max=10)
  @Pattern(regexp = "^.*(?=^.{8,10}$)(?=.*\\d)(?=.*[a-zA-Z])(?=.*[!@#$%^&+=]).*$", message = "숫자, 문자, 특수문자 포함 8~10자리 이내")
  private String pw;

  @NotBlank
  @Size(min=8, max=10)
  @Pattern(regexp = "^.*(?=^.{8,10}$)(?=.*\\d)(?=.*[a-zA-Z])(?=.*[!@#$%^&+=]).*$", message = "숫자, 문자, 특수문자 포함 8~10자리 이내")
  private String pwChk;

  @NotBlank
  @Size(min=1, max=8)
  @Pattern(regexp = "^[가-힝]*$", message = "한글이여야 합니다")
  private String name;

  @NotBlank
  @Email
  @Size(min=4, max=50)
  private String email;

  @NotBlank
  private String nickname;

}
