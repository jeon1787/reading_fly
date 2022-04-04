package com.kh.reading_fly.web.controller;

import com.kh.reading_fly.domain.booktest.dto.BookTestDTO;
import com.kh.reading_fly.domain.booktest.svc.BookTestSVC;
import com.kh.reading_fly.domain.revirwtest.dto.ReviewReqDTO;
import com.kh.reading_fly.domain.revirwtest.svc.ReviewTestSVC;
import com.kh.reading_fly.web.form.booktest.AddForm;
import com.kh.reading_fly.web.form.booktest.DetailForm;
import com.kh.reading_fly.web.form.booktest.EditForm;
import com.kh.reading_fly.web.form.booktest.ItemForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/booktest")
public class BookTestController {

  private final BookTestSVC bookTestSVC;
  private final ReviewTestSVC reviewTestSVC;

  //전체목록
  @GetMapping
  public String list(Model model){
    log.info("list() 호출됨!");

    List<BookTestDTO> list = bookTestSVC.selectAll();
    List<ItemForm> items =new ArrayList<>();

    for(BookTestDTO bookTestDTO : list){
      ItemForm item = new ItemForm();
      BeanUtils.copyProperties(bookTestDTO, item);

      items.add(item);
    }

    model.addAttribute("items", items);

    return "booktest/listForm";
  }

  //상세화면
  @GetMapping("/{isbn}/detail")
  public String detail(@PathVariable Long isbn,
                       Model model){
    log.info("detail() 호출됨!");

    BookTestDTO bookTestDTO = bookTestSVC.selectOne(isbn);
    DetailForm detailForm = new DetailForm();
    BeanUtils.copyProperties(bookTestDTO, detailForm);

    model.addAttribute("detailForm", detailForm);
    log.info("detailForm={}", detailForm);

    List<ReviewReqDTO> rvlist = reviewTestSVC.allReview(isbn);
    model.addAttribute("review", rvlist);



    log.info("detail() 호출됨!끝");
    return "booktest/detailForm";
//    return "booktest/test/testdetailForm";
  }

  //작성모드
  @GetMapping("/add")
  public String add(@ModelAttribute AddForm addForm,
                    HttpServletRequest request
  ){
    log.info("add() 호출됨!");

    HttpSession session = request.getSession(false);
    log.info("referer={}", request.getHeader("referer"));

    //비로그인 상태인 경우 => 로그인 페이지로 이동
    if (session == null || session.getAttribute("loginMember") == null || session.getAttribute("loginMember").equals("")){
      return "redirect:/login";
    }

    return "booktest/addForm";
  }

  //작성처리
  @PostMapping("/add")
  public String addBoard(@ModelAttribute AddForm addForm,
                         HttpSession session,
                         RedirectAttributes redirectAttributes
  ){
    log.info("addBoard() 호출됨!");

    BookTestDTO bookTestDTO = new BookTestDTO();
    BeanUtils.copyProperties(addForm, bookTestDTO);

    BookTestDTO writedBookTestDTO = bookTestSVC.create(bookTestDTO);
    redirectAttributes.addAttribute("isbn", writedBookTestDTO.getIsbn());

    return "redirect:/booktest/{isbn}/detail";
  }

  //수정모드
  @GetMapping("/{isbn}/edit")
  public String edit(@PathVariable Long isbn,
                     Model model
  ){
    log.info("edit() 호출됨!");

    BookTestDTO bookTestDTO = bookTestSVC.selectOne(isbn);

    EditForm editForm = new EditForm();
    BeanUtils.copyProperties(bookTestDTO, editForm);

    model.addAttribute("editForm", editForm);

    return "booktest/editForm";
  }

  //수정처리
  @PatchMapping("/{isbn}/edit")
  public String editBoard(@ModelAttribute EditForm editForm,
                          @PathVariable Long isbn,
                          HttpSession session,
                          RedirectAttributes redirectAttributes
  ){
    log.info("editBoard() 호출됨!");

    BookTestDTO bookTestDTO = new BookTestDTO();

    bookTestDTO.setIsbn(isbn);
    BeanUtils.copyProperties(editForm, bookTestDTO);

    BookTestDTO modifiedBoardTestDTO = bookTestSVC.update(bookTestDTO);

    redirectAttributes.addAttribute("isbn", modifiedBoardTestDTO.getIsbn());

    return "redirect:/booktest/{isbn}/detail";
  }








}
