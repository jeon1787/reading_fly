package com.kh.reading_fly.domain.booktest.svc;

import com.kh.reading_fly.domain.booktest.dto.BookTestDTO;

import java.util.List;

public interface BookTestSVC {

  //전체조회
  List<BookTestDTO> selectAll();
  //상세조회
  BookTestDTO selectOne(Long isbn);
  //등록
  BookTestDTO create(BookTestDTO bookTestDTO);
  //수정
  BookTestDTO update(BookTestDTO bookTestDTO);




}
