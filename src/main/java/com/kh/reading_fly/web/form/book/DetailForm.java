package com.kh.reading_fly.web.form.book;

import lombok.*;

import java.sql.Date;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class DetailForm {
    private String isbn;    //    isbn NUMBER(13) not null,             -- 도서번호13
    private String title;    //    title VARCHAR2(100) not null,         -- 제목
    private String thumbnail;    //    thumbnail VARCHAR2(200) not null,     -- 표지URL

    private Long snum; //NUMBER(15) not null,     -- 도서등록번호
    private Long spage;  //NUMBER(15),            -- 총페이지
    private Long sisbn; //NUMBER(13) not null,    -- ISBN
    private String sid; //VARCHAR2(40) not null     -- 아이디

    private Long dnum; //NUMBER(15) not null,       -- 기록No
    private LocalDate ddate; //DATE not null,            -- 기록일자
    private Long dpage; //NUMBER(15) not null,      -- 기록쪽수
    private Long dsnum; //NUMBER(15) not null,     -- 도서등록No
    private String did; //VARCHAR2(40) not null       -- 아이디
}
