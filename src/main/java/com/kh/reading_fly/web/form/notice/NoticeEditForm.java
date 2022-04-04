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
  @Size(min=1, max = 20, message = "최소 1자 최대 20자")
  private String nTitle;
  @NotBlank(message = "내용을 입력해주세요")
  @Size(min=1, max = 1000, message = "최소 1자 최대 1000자")
  private String nContent;

  private int nHit;

  private List<MultipartFile> files;  // 첨부파일
}
