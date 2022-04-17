package com.kh.reading_fly.domain.book.dao;

import com.kh.reading_fly.domain.book.dto.Book;

import java.util.List;

public interface BookDAO {

    //도서 등록
    Book saveBook(Book book);
    
    //도서 등록 여부
    int count(String isbn);

    //책장 등록
    Long saveShelf(Book book);

    //독서기록 등록(최초)
    Long saveDoc(Book book);

    //책장목록
    List<Book> listShelf(String id);

    //책장삭제
    int delShelf(String id, String isbn);

    //기록삭제
    int delDoc(String id, String isbn);

    //최근기록 단건 조회
    Book detailDoc(String id, String isbn);

    //기록 목록 조회
    List<Book> listDoc(String id, String isbn);

    //기록 등록
    Long insertDoc(Book book);

    //총페이지 수정
    int editDoc(Book book);

    //독서기록 단건 삭제
    int removeDoc(String id, Long dnum);
}
