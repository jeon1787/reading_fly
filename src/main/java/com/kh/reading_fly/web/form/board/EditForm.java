package com.kh.reading_fly.web.form.board;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter@Setter
@ToString
public class EditForm {
  private Long bnum;
  private String btitle;
  private String bcontent;
}
