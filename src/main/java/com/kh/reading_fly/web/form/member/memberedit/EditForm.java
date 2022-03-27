package com.kh.reading_fly.web.form.member.memberedit;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.sql.Date;
import java.time.LocalDateTime;

@Data
public class EditForm {

  private String id;

  private String name;

  @NotEmpty(message = "닉네임을 입력하세요")
  private String nickname;

  @NotEmpty(message = "Email을 입력하세요")
  @Email
  private String email;

//    private LocalDateTime signup_dt;
//
//  private String leave_fl;
//
//  private LocalDateTime leave_dt;




}
