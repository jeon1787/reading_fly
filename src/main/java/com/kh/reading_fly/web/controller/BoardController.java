package com.kh.reading_fly.web.controller;

import com.kh.reading_fly.domain.board.dto.BoardDTO;
import com.kh.reading_fly.domain.board.svc.BoardSVC;
import com.kh.reading_fly.domain.comment.svc.CommentSVC;
import com.kh.reading_fly.domain.common.paging.PageCriteria;
import com.kh.reading_fly.web.form.board.AddForm;
import com.kh.reading_fly.web.form.board.DetailForm;
import com.kh.reading_fly.web.form.board.EditForm;
import com.kh.reading_fly.web.form.board.ItemForm;
import com.kh.reading_fly.web.form.member.login.LoginMember;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {

  private final BoardSVC boardSVC;
  private final CommentSVC commentSVC;

  @Autowired
  @Qualifier("pc10")
  private PageCriteria pc;

  //전체목록
  @GetMapping({"",
               "/{reqPage}"})
  public String list(@PathVariable(required = false) Optional<Integer> reqPage,
                     Model model){
    log.info("list() 호출됨!");

    //요청 없으면 1페이지 출력
    Integer page = reqPage.orElse(1);
    //요청페이지의 시작레코드, 종료레코드 set
    pc.getRc().setReqPage(page);

    //총레코드수
    pc.setTotalRec(boardSVC.totalCount());
    List<BoardDTO> list = boardSVC.findAll(pc.getRc().getStartRec(), pc.getRc().getEndRec());

    List<ItemForm> items =new ArrayList<>();
    for(BoardDTO boardDTO : list){
      ItemForm item = new ItemForm();
//      item.setBnum(boardDTO.getBnum());
//      item.setBtitle(boardDTO.getBtitle());
//      item.setBhit(boardDTO.getBhit());
//      item.setNickname(boardDTO.getNickname());
//copyProperties 의 경우 필드명이 같아도 타입이 다르면 복사되지 않는다(budate=null)
      BeanUtils.copyProperties(boardDTO, item);

      LocalDate boardDate = boardDTO.getBudate().toLocalDate();
//      log.info("boardDate={}", boardDate);
      LocalDate today = LocalDate.now();
//      log.info("today={}", today);

      if(boardDate.equals(today)){//오늘 작성된 글이면
        item.setBudate(boardDTO.getBudate().toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm")).toString());
      }else{//오늘 이전에 작성된 글이면
        item.setBudate(boardDTO.getBudate().toLocalDate().toString());
      }

      items.add(item);
    }

    model.addAttribute("items", items);
    model.addAttribute("pc", pc);

    return "board/listForm";
  }

  //상세화면
  @GetMapping("/{bnum}/detail")
  public String detail(@PathVariable Long bnum, Model model){
    log.info("detail() 호출됨!");

    BoardDTO boardDTO = boardSVC.findByBnum(bnum);
    DetailForm detailForm = new DetailForm();

//    detailForm.setBnum(boardDTO.getBnum());
//    detailForm.setBtitle(boardDTO.getBtitle());
//    detailForm.setBcontent(boardDTO.getBcontent());
//    detailForm.setBhit(boardDTO.getBhit());
//    detailForm.setBid(boardDTO.getBid());
//    detailForm.setNickname(boardDTO.getNickname());
//copyProperties 의 경우 필드명이 같아도 타입이 다르면 복사되지 않는다(budate=null)
    BeanUtils.copyProperties(boardDTO, detailForm);

    LocalDate boardDate = boardDTO.getBudate().toLocalDate();
//    log.info("boardDate={}", boardDate);
    LocalDate today = LocalDate.now();
//    log.info("today={}", today);

    if(boardDate.equals(today)){//오늘 작성된 글이면
      detailForm.setBudate(boardDTO.getBudate().toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm")).toString());
    }else{//오늘 이전에 작성된 글이면
      detailForm.setBudate(boardDTO.getBudate().toLocalDate().toString());
    }

    model.addAttribute("detailForm", detailForm);
    log.info("detailForm={}", detailForm);

    log.info("detail() 호출됨!끝");
    return "board/detailForm";
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

    return "board/addForm";
  }

  //작성처리
  @PostMapping("/add")
  public String addBoard(@Valid @ModelAttribute AddForm addForm,
                         BindingResult bindingResult,      // 폼객체에 바인딩될때 오류내용이 저장되는 객체
                         HttpSession session,
                         RedirectAttributes redirectAttributes
  ){
    log.info("addBoard() 호출됨!");

    if(bindingResult.hasErrors()){
      log.info("/board/add/bindingResult={}",bindingResult);
      return "board/addForm";
    }

    BoardDTO boardDTO = new BoardDTO();
//    boardDTO.setBtitle(addForm.getBtitle());
//    boardDTO.setBcontent(addForm.getBcontent());
    BeanUtils.copyProperties(addForm, boardDTO);
    
    //보안을 위해 세션에서 id 값을 받아온다 만약 비로그인상태거나 세션이 만료된 상태면 인터셉터에서 처리한다
    LoginMember loginMember = (LoginMember) session.getAttribute("loginMember");//세션에서 로그인 정보 가져오기
    boardDTO.setBid(loginMember.getId());

    BoardDTO writedBoardDTO = boardSVC.write(boardDTO);
    redirectAttributes.addAttribute("bnum", writedBoardDTO.getBnum());

    return "redirect:/board/{bnum}/detail";
  }
  
  //수정모드
  @GetMapping("/{bnum}/edit")
  public String edit(@PathVariable Long bnum,
                     Model model
  ){
    log.info("edit() 호출됨!");

    BoardDTO boardDTO = boardSVC.findByBnum(bnum);

    EditForm editForm = new EditForm();
//    editForm.setBnum(boardDTO.getBnum());
//    editForm.setBtitle(boardDTO.getBtitle());
//    editForm.setBcontent(boardDTO.getBcontent());
    BeanUtils.copyProperties(boardDTO, editForm);

    model.addAttribute("editForm", editForm);

    return "board/editForm";
  }
  
  //수정처리
  @PatchMapping("/{bnum}/edit")
  public String editBoard(@Valid @ModelAttribute EditForm editForm,
                          BindingResult bindingResult,      // 폼객체에 바인딩될때 오류내용이 저장되는 객체
                         @PathVariable Long bnum,
                         HttpSession session,
                         RedirectAttributes redirectAttributes
  ){
    log.info("editBoard() 호출됨!");

    if(bindingResult.hasErrors()){
      log.info("/board/add/bindingResult={}",bindingResult);
      return "board/editForm";
    }

    BoardDTO boardDTO = new BoardDTO();

    boardDTO.setBnum(bnum);
//    boardDTO.setBtitle(editForm.getBtitle());
//    boardDTO.setBcontent(editForm.getBcontent());
    BeanUtils.copyProperties(editForm, boardDTO);
    LoginMember loginMember = (LoginMember) session.getAttribute("loginMember");//세션에서 로그인 정보 가져오기
    boardDTO.setBid(loginMember.getId());

    BoardDTO modifiedBoardDTO = boardSVC.modify(boardDTO);

    redirectAttributes.addAttribute("bnum", modifiedBoardDTO.getBnum());

    return "redirect:/board/{bnum}/detail";
  }

  //삭제처리
  @GetMapping("/{bnum}/del")
  public String delete(@PathVariable Long bnum,
                       HttpSession session
  ){
    log.info("del() 호출됨!");

    LoginMember loginMember = (LoginMember)session.getAttribute("loginMember");

    log.info("댓글유무={}", commentSVC.findAll(bnum).size());

    //댓글 없는 게시글 완전 삭제
    if(commentSVC.findAll(bnum).size() == 0){
      log.info("게시글 완전 삭제");
      boardSVC.removeBoard(bnum, loginMember.getId());

      return "redirect:/board";

    //댓글 있는 게시글 본문 삭제
    }else{
      log.info("게시글 본문 삭제");
      boardSVC.removeContentOfBoard(bnum, loginMember.getId());

      return "redirect:/board/{bnum}/detail";
    }
  }

}
