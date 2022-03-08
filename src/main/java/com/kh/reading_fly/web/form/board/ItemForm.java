package com.kh.reading_fly.web.form.board;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemForm {
  private Long num;            //게시글번호
  private String title;        //제목
  private String date;         //날짜
  private Long hit;            //조회수
  private String nickname;           //작성자
}
