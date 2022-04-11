package com.kh.reading_fly.web.form.book;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class EditForm {

    private Long spage;  //NUMBER(15),            -- 총페이지

    private Long dnum; //NUMBER(15) not null,       -- 기록No
    private LocalDate ddate; //DATE not null,            -- 기록일자
    private Long dpage; //NUMBER(15) not null,      -- 기록쪽수
    private Long dsnum; //NUMBER(15) not null,     -- 도서등록No
    private String did; //VARCHAR2(40) not null       -- 아이디
}