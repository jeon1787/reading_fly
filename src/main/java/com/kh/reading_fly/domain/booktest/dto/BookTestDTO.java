package com.kh.reading_fly.domain.booktest.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Data
public class BookTestDTO {

 private Long isbn;          //  도서번호 isbn NUMBER(13) not null
  private String title;        //  제목 title VARCHAR2(100) not null
  private  String  author;       // 저자 author VARCHAR2(100)
  private String  publisher;      // 출판사 publisher VARCHAR2(100)
  private String  translator;      // 번역가 translator VARCHAR2(100)
  private String  thumbnail;        //표지 url thumbnail VARCHAR2(100) not null
  private String publication_dt;     // 출판일 publication_dt DATE





}
