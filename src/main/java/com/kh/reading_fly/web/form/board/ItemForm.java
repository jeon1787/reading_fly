package com.kh.reading_fly.web.form.board;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemForm {
  private Long bnum;            //게시글번호
  private String btitle;        //제목
  private String budate;         //날짜
  private Long bhit;            //조회수
  private String nickname;           //작성자
}
