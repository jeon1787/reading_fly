package com.kh.reading_fly.domain.review.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ReviewDTO {

  private Long rnum;      // 리뷰No rnum NUMBER(15) not null,
  private String rcontent;  // 리뷰내용 rcontent VARCHAR2(100) not null,
  private LocalDateTime rcdate;    // 리뷰작성일 rcdate TIMESTAMP default systimestamp,
  private LocalDateTime rudate;    // 리뷰수정일 rudate TIMESTAMP default systimestamp,
  private Long rstar;     //  별점 rstar NUMBER(1) default 0,
  private Long risbn;     //  도서번호 risbn NUMBER(13) not null,
  private String rid;       //  아이디 rid  VARCHAR2(40) not null
  private String nickname;        //닉네임 - 리뷰 작성자를 표기 위함



}
