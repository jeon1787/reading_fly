package com.kh.reading_fly.domain.book.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Date;


@Getter
@Setter
@ToString
public class BookRequest {
    private String searchWord; //검색어

    private String isbn;    //    isbn NUMBER(13) not null,             -- 도서번호13
    private String title;    //    title VARCHAR2(100) not null,         -- 제목
    private String bcontents;
    private String authors;    //    author VARCHAR2(100),                 -- 저자
    private String publisher;    //    publisher VARCHAR2(100),              -- 출판사
    private String translators;    //    translator VARCHAR2(100),             -- 번역가
    private String thumbnail;    //    thumbnail VARCHAR2(200) not null,     -- 표지URL
    private Date publisher_dt;    //    publication_dt DATE                   -- 출판일

    private Long spage;

}
