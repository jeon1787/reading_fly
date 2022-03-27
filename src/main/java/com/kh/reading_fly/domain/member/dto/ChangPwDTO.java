package com.kh.reading_fly.domain.member.dto;

import lombok.Data;

@Data
public class ChangPwDTO {
  

  private String id;
  private String pw;
  private	String pwChk;
  private String pwChk2;


}
