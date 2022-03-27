package com.kh.reading_fly.web.form.member.login;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class LoginMember {
  private String id;
  private String nickname;
  private String email;

  private int admin_fl;

}
