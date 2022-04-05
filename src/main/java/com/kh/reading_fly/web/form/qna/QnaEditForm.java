package com.kh.reading_fly.web.form.qna;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Data
public class QnaEditForm {
  private Long qNum;             //게시글 번호
  @NotBlank(message = "제목을 입력해주세요")
  @Size(min=1, max = 20, message = "최소 1자 최대 20자")
  private String qTitle;         //제목
  private String qNickname;      //별칭
  @NotBlank(message = "내용을 입력해주세요")
  @Size(min=1, max = 1000, message = "최소 1자 최대 1000자")
  private String qContent;      //내용
  private int qHit;             //조회수

  private List<MultipartFile> files;  // 첨부파일
}
