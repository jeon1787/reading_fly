package com.kh.reading_fly.web.form.member.find;

import lombok.Data;

@Data
public class ChangPwReq {

  private String id;
  private String pw;
  private String pwChk;
  private String pwChk2;


}
