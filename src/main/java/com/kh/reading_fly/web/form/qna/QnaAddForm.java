package com.kh.reading_fly.web.form.qna;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Data
public class QnaAddForm {
  @NotBlank(message = "제목을 입력해주세요")
  @Size(max = 20, message = "글자수 20자를 초과할 수 없습니다.")
  private String qTitle;         //제목
  private String qEmail;         //EMAIL
  private String qNickname;      //별칭
  @NotBlank(message = "내용을 입력해주세요")
  private String qContent;      //내용

  private List<MultipartFile> files;  // 첨부파일
}
