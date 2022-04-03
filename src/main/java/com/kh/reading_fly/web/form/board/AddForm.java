package com.kh.reading_fly.web.form.board;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter@Setter
@ToString
public class AddForm {
  @NotBlank
  @Size(max = 20)
  private String btitle;        //제목
  @NotBlank
  @Size(max = 1000)
  private String bcontent;      //본문
}
