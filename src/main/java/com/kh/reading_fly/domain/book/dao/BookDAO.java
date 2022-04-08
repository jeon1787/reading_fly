package com.kh.reading_fly.domain.book.dao;

import com.kh.reading_fly.domain.book.dto.Book;

import java.util.List;

public interface BookDAO {

    //도서등록
    Book saveBook(Book book);

    //도서등록
    Long saveBookShelf(Book book);

    //도서등록
    Book saveDocument(Book book);

    //목록
    List<Book> list(String id);

    //조회
    List<Book> detail(String id, String isbn);

    //수정
    Long update(String id, String isbn, Book book);

    //삭제
    int delete(String isbn);
}
