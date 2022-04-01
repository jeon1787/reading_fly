package com.kh.reading_fly.web.form.booktest;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter@Setter
@ToString
public class EditForm {
  private Long isbn;
  private String title;
  private String thumbnail;
}
