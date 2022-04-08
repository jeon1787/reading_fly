package com.kh.reading_fly.domain.book.svc;

import com.kh.reading_fly.domain.book.dao.BookDAO;
import com.kh.reading_fly.domain.book.dto.Book;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class BookSVCImpl implements BookSVC{

    private final BookDAO bookDAO;

    public BookSVCImpl(BookDAO bookDAO) {
        this.bookDAO = bookDAO;
    }

    @Override
    public Book saveBook(Book book) {
        return bookDAO.saveBook(book);
    }

    @Override
    public Long saveBookShelf(Book book) {
        return bookDAO.saveBookShelf(book);
    }

    @Override
    public Book saveDocument(Book book) {
        return bookDAO.saveDocument(book);
    }

    @Override
    public List<Book> list(String id) {
        return bookDAO.list(id);
    }

    @Override
    public List<Book> detail(String id, String isbn) {
        return bookDAO.detail(id, isbn);
    }

    @Override
    public Long update(String id, String isbn, Book book) {
        return bookDAO.update(id, isbn, book);
    }

    @Override
    public int delete(String isbn) {
        return bookDAO.delete(isbn);
    }
}
