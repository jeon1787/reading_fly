package com.kh.reading_fly.domain.book.svc;

import com.kh.reading_fly.domain.book.dao.BookDAO;
import com.kh.reading_fly.domain.book.dto.Book;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class BookSVCImpl implements BookSVC{

    private final BookDAO bookDAO;

    /**
     * 도서등록
     * @param book
     * @return
     */
    @Override
    public Book saveBook(Book book) {
        return bookDAO.saveBook(book);
    }

    /**
     * 책장 등록
     * @param book
     * @return
     */
    @Override
    public Long saveShelf(Book book) {
        return bookDAO.saveShelf(book);
    }

    /**
     * 독서기록 등록(최초)
     * @param book
     * @return
     */
    @Override
    public Long saveDoc(Book book) {
        return bookDAO.saveDoc(book);
    }

    /**
     * 책장목록
     * @param id
     * @return
     */
    @Override
    public List<Book> listShelf(String id) {
        return bookDAO.listShelf(id);
    }

    /**
     *
     * @param id
     * @param isbn
     * @return
     */
    @Override
    public int delShelf(String id, String isbn) {
        return bookDAO.delShelf(id, isbn);
    }

    /**
     * 책장삭제
     * @param id
     * @param isbn
     * @return
     */
    @Override
    public int delDoc(String id, String isbn) {
        return bookDAO.delDoc(id, isbn);
    }

    /**
     * 기록삭제
     * @param id
     * @param isbn
     * @return
     */
    @Override
    public Book detailDoc(String id, String isbn) {
        return bookDAO.detailDoc(id, isbn);
    }

    /**
     * 기록 목록 조회
     * @param id
     * @param isbn
     * @return
     */
    @Override
    public List<Book> listDoc(String id, String isbn) {
        return bookDAO.listDoc(id, isbn);
    }

    /**
     * 기록 등록
     * @param id
     * @param isbn
     * @param book
     * @return
     */
    @Override
    public Long insertDoc(String id, String isbn, Book book) {
        return bookDAO.insertDoc(id, isbn, book);
    }

    /**
     * 총페이지 수정
     * @param id
     * @param isbn
     * @param spage
     * @param book
     * @return
     */
    @Override
    public int editDoc(String id, String isbn, Long spage, Book book) {
        return bookDAO.editDoc(id, isbn, spage, book);
    }

    /**
     * 독서기록 단건 삭제
     * @param dnum
     * @return
     */
    @Override
    public int removeDoc(Long dnum) {
        return bookDAO.removeDoc(dnum);
    }
}
