package com.kh.reading_fly.web.form.notice;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Setter
@Getter
@ToString
public class NoticeEditForm {
  private Long nNum;
  @NotBlank(message = "제목을 입력해주세요")
  @Size(max = 20, message = "글자수 20자를 초과할 수 없습니다.")
  private String nTitle;
  @NotBlank(message = "내용을 입력해주세요")
  private String nContent;

  private int nHit;

  private List<MultipartFile> files;  // 첨부파일
}
