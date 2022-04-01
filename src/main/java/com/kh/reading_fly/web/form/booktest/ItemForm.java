package com.kh.reading_fly.web.form.booktest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemForm {
  private Long isbn;
  private String title;
//  private String thumbnail;
}
