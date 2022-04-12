package com.kh.reading_fly.web.controller;

import com.kh.reading_fly.domain.book.dto.Book;
import com.kh.reading_fly.domain.book.dto.BookRequest;
import com.kh.reading_fly.domain.book.svc.BookSVC;
import com.kh.reading_fly.web.form.book.DetailForm;
import com.kh.reading_fly.web.form.book.SaveForm;
import com.kh.reading_fly.web.form.book.UpdateForm;
import com.kh.reading_fly.web.form.member.SessionConst;
import com.kh.reading_fly.web.form.member.login.LoginMember;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Slf4j
@Controller
@AllArgsConstructor
@RequestMapping("/book")
public class BookController {

    private final BookSVC bookSVC;

    //검색
    @GetMapping({"/search", "/search/{inputWord}"})
    public String search(@Valid@ModelAttribute BookRequest bookRequest,
                         @PathVariable(required = false) Optional<String> inputWord,
                        Model model){
        log.info("search={}, bookRequest");
        log.info("keyword={}", inputWord.orElse(""));
        model.addAttribute("keyword", inputWord.orElse(""));

        return "book/bookSearch";
    }

    //검색결과
    @PostMapping("/info")
    public String info(@Valid@ModelAttribute BookRequest bookRequest,
                       Model model){

        log.info("info={}",bookRequest);
        model.addAttribute("bookRequest", bookRequest);

        return "book/bookInfo";
    }

    //등록처리
    @PostMapping("/save")
    public String save(@Valid@ModelAttribute SaveForm saveForm,
                       HttpSession session){
        log.info("통과");
        log.info("saveForm={}", saveForm);
        LoginMember loginMember = (LoginMember)session.getAttribute(SessionConst.LOGIN_MEMBER);
        Book book = new Book();
//        if(saveForm.getIsbn().split(0,9))
        BeanUtils.copyProperties(saveForm, book);
        book.setSid(loginMember.getId());
//        book.setSisbn(saveForm.getIsbn());
        book.setDid(loginMember.getId());
        if(bookSVC.count(saveForm.getIsbn()) == 0 ){
            bookSVC.saveBook(book);
        }
//        book.setSpage(saveForm.getSpage());
        book.setDpage(0L);
        log.info("book.getSpage()={}", book.getSpage());
        if(book.getSpage() == null){
            book.setSpage(0L);
        }
        Long snum = bookSVC.saveShelf(book);
        book.setDsnum(snum);
        bookSVC.saveDoc(book);
        return "redirect:/book/list";
    }

    //목록
    @GetMapping("/list")
    public String list(HttpSession session,
                       Model model){
        LoginMember loginMember = (LoginMember)session.getAttribute(SessionConst.LOGIN_MEMBER);
        String id = loginMember.getId();
        List<Book> listBook = bookSVC.listShelf(id);
        model.addAttribute("listBook", listBook);
        return "book/bookList";
    }

    //상세
    @GetMapping("/{isbn}/detail")
    public String detail(@PathVariable String isbn,
                             HttpSession session,
                             Model model){
        LoginMember loginMember = (LoginMember)session.getAttribute(SessionConst.LOGIN_MEMBER);
        String id = loginMember.getId();

        Book detailBook = bookSVC.detailDoc(id, isbn);
        DetailForm detailForm = new DetailForm();
        BeanUtils.copyProperties(detailBook, detailForm);
        model.addAttribute("detailForm", detailForm);
        return "book/bookDetail";
    }
}
