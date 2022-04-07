package com.kh.reading_fly.domain.book.svc;

import com.kh.reading_fly.domain.book.dto.Book;
import com.kh.reading_fly.domain.book.dto.BookRequest;

import java.util.List;

public interface BookSVC {

    //도서등록
    Book saveBook(Book book);

    //책장등록
    Long saveBookShelf(Book book);

    //독서기록등록
    Book saveDocument(Book book);

    //목록
    List<Book> list(String id);

    //조회
    Book detail(String isbn);

    //수정
    Book update(String isbn, Book book);

    //삭제
    int delete(String isbn);
}
