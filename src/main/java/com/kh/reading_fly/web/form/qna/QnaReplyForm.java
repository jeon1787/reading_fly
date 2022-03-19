package com.kh.reading_fly.web.form.qna;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class QnaReplyForm {
  @NotBlank
  @Size(min=5,max=50)
  private String qTitle;         //  제목
  @NotBlank
  @Size(min=3,max=15)
  private String qNickname;      //  별칭
  @NotBlank
  @Size(min=5)
  private String qContent;      //  내용
}
