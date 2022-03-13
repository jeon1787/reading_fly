package com.kh.reading_fly.domain.notice.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class NoticeDTO {
  private Long nNum;        //공지 아이디
  private String nTitle;       //제목
  private String nContent;       //본문
  private Long nHit;            //조회수
  private LocalDateTime nCDate;  //생성일시
  private LocalDateTime nUDate;  //수정일시
}
