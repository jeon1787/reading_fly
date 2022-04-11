package com.kh.reading_fly.web.controller;

import com.kh.reading_fly.domain.book.dto.Book;
import com.kh.reading_fly.domain.book.svc.BookSVC;
import com.kh.reading_fly.web.form.book.EditForm;
import com.kh.reading_fly.web.form.book.InsertForm;
import com.kh.reading_fly.web.form.comment.ApiResult;
import com.kh.reading_fly.web.form.member.SessionConst;
import com.kh.reading_fly.web.form.member.login.LoginMember;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.List;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/api/book")
public class APiBookController {

    private final BookSVC bookSVC;

    //도서등록
    @PostMapping
    public ApiResult<Book> save(@ModelAttribute Book book){
        Book savedBook = bookSVC.saveBook(book);
        ApiResult<Book> result = new ApiResult<>("00", "성공", savedBook);
        return result;
    }
    //목록
    @GetMapping
    public ApiResult<List<Book>> list(HttpSession session){
        LoginMember loginMember = (LoginMember)session.getAttribute(SessionConst.LOGIN_MEMBER);
        List<Book> listBook = bookSVC.listShelf(loginMember.getId());
        ApiResult<List<Book>> result = null;
        if(listBook.size() != 0) {
            result = new ApiResult<>("00", "success", listBook);
        }else{
            result = new ApiResult<>("99", "데이터 없음", listBook);
        }
        return result;
    }

    //삭제(책장, 독서기록)
    @DeleteMapping("/{isbn}")
    public ApiResult<Boolean> del(@PathVariable String isbn,
                                  HttpSession session){
        LoginMember loginMember = (LoginMember)session.getAttribute(SessionConst.LOGIN_MEMBER);
        String id = loginMember.getId();
        int delDoc = bookSVC.delDoc(id, isbn);
        int delShelf = bookSVC.delShelf(id, isbn);
        ApiResult<Boolean> result = null;
        if(delDoc == 1 && delShelf == 1){
            result = new ApiResult<>("00", "둘다삭제", true);
        }else if(delDoc == 1 && delShelf == 0){
            result = new ApiResult<>("99", "하나만삭제", false);
        }else if(delDoc == 0 && delShelf == 1){
            result = new ApiResult<>("99", "하나만삭제", false);
        }else{
            result = new ApiResult<>("99", "둘다실패", false);
        }
        return result;
    }

    //기록 목록 조회
    @GetMapping("/{id}/list")
    public ApiResult<List<Book>> listDoc(@PathVariable String isbn,
                                         HttpSession session){
        LoginMember loginMember = (LoginMember)session.getAttribute(SessionConst.LOGIN_MEMBER);
        String id = loginMember.getId();
        List<Book> listDoc= bookSVC.listDoc(id, isbn);
        ApiResult<List<Book>> result = null;
        if(listDoc.size() != 0){
            result = new ApiResult<>("00", "기록목록조회", listDoc);
        }else{
            result = new ApiResult<>("99", "데이터없음", listDoc);
        }
        return result;
    }

    //기록 등록
    @PostMapping("/save")
    public ApiResult<Book> insertDoc(@RequestBody InsertForm insertForm,
                                     HttpSession session){
        log.info("insertForm={}",insertForm);
        LoginMember loginMember = (LoginMember)session.getAttribute(SessionConst.LOGIN_MEMBER);
        String id = loginMember.getId();
        Book book = new Book();
        BeanUtils.copyProperties(insertForm, book);
        book.setDid(id);
        book.setSid(id);
        Long dnum = bookSVC.insertDoc(book);
        Book insertDoc = new Book();
        insertDoc.setDnum(dnum);
//        if(dnum > 0){
//            insertDoc = bookSVC.detailDoc(id, isbn);
//            BeanUtils.copyProperties(insertDoc, book);
//        }
        ApiResult<Book> result = new ApiResult<>("00", "기록등록", book);
        return result;
    }

    //독서기록 단건 삭제
    @DeleteMapping("/{dnum}")
    public ApiResult<Boolean> removeDoc(@PathVariable Long dnum){
        int removeDoc = bookSVC.removeDoc(dnum);
        ApiResult<Boolean> result = null;
        if(removeDoc == 1){
            result = new ApiResult<>("00", "기록 단건 삭제", true);
        }else{
            result = new ApiResult<>("99", "fail", false);
        }
        return result;
    }
}
