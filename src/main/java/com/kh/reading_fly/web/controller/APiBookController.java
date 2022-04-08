package com.kh.reading_fly.web.controller;

import com.kh.reading_fly.domain.book.dto.Book;
import com.kh.reading_fly.domain.book.svc.BookSVC;
import com.kh.reading_fly.web.form.comment.ApiResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Controller
@RestController
@RequestMapping("/api/book")
public class APiBookController {

    private final BookSVC bookSVC;

    public APiBookController(BookSVC bookSVC) {

        this.bookSVC = bookSVC;
    }

    //도서등록
    @PostMapping
    public ApiResult<Book> save(@RequestBody Book book){

        Book savedBook = bookSVC.saveBook(book);

        ApiResult<Book> result = new ApiResult<>("00", "성공", savedBook);

        return result;
    }
    //목록
    @GetMapping
    public ApiResult<List<Book>> list(String id){

        List<Book> listBook = bookSVC.list(id);

        ApiResult<List<Book>> result = null;

        if(listBook.size() != 0) {

            result = new ApiResult<>("00", "success", listBook);

        }else{

            result = new ApiResult<>("99", "데이터 없음", listBook);
        }

        return result;
    }
//
    //조회
    @PostMapping("/{isbn}")
    public ApiResult<List<Book>> detail(@PathVariable String id, @PathVariable String isbn){

        List<Book> detailBook = bookSVC.detail(id, isbn);


        ApiResult<List<Book>> result = null;

        if(detailBook != null) {

            result = new ApiResult<>("00", "success", detailBook);

        }else{

            result = new ApiResult<>("99", "조회하고자하는 데이터가 없습니다.", detailBook);

        }

        return result;
    }

    //수정
    @PatchMapping("/{isbn}")
    public ApiResult<Book> update(@PathVariable String id, @PathVariable String isbn, @RequestBody Book book){

        Long updateBook = bookSVC.update(id, isbn, book);

        book.setDdate(LocalDate.now());

        ApiResult<Book> result = null;

        if(updateBook != null){

//            result = new ApiResult<>("00","success", updateBook);
//
//        }else{
//
//            result = new ApiResult<>("99","fail", updateBook);
        }

        return result;
    }

    //삭제
    @DeleteMapping("/{isbn}")
    public ApiResult<Boolean> delete(@PathVariable String isbn){

        int deleteBook = bookSVC.delete(isbn);

        ApiResult<Boolean> result = null;

        if(deleteBook == 1){

            result = new ApiResult<>("00","success",true);

        }else{

            result = new ApiResult<>("99","fail",false);
        }
        return result;
    }
}
