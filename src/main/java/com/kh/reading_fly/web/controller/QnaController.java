package com.kh.reading_fly.web.controller;

import com.kh.reading_fly.domain.qna.dto.QnaDTO;
import com.kh.reading_fly.domain.qna.svc.QnaSVC;
import com.kh.reading_fly.web.form.member.login.LoginMember;
import com.kh.reading_fly.web.form.qna.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/qna")
@RequiredArgsConstructor
public class QnaController {
  private final QnaSVC qnaSVC;

  //작성양식
  @GetMapping("/add")
//  public String addForm(Model model) {
//    model.addAttribute("addForm", new AddForm());
//    return "bbs/addForm";
//  }
  public String addForm(
      Model model,
      HttpSession session) {

    LoginMember loginMember = (LoginMember) session.getAttribute("loginMember");//세션에서 로그인 정보 가져오기
//    LoginMember loginMember = (LoginMember)session.getAttribute(SessionConst.LOGIN_MEMBER);

    QnaAddForm qnaAddForm = new QnaAddForm();
//    qnaAddForm.setQEmail(loginMember.getEmail());
    qnaAddForm.setQNickname(loginMember.getNickname());
    model.addAttribute("qnaAddForm", qnaAddForm);

    return "qna/qnaAddForm";
  }

  //작성처리
  @PostMapping("/add")
  public String add(
      @Valid @ModelAttribute QnaAddForm qnaAddForm,
      BindingResult bindingResult,
      HttpSession session,
      RedirectAttributes redirectAttributes) {

    if(bindingResult.hasErrors()) {
      return "qna/qnaAddForm";
    }
    QnaDTO qna = new QnaDTO();
    BeanUtils.copyProperties(qnaAddForm, qna);

    //세션 가져오기
    LoginMember loginMember = (LoginMember) session.getAttribute("loginMember");//세션에서 로그인 정보 가져오기
//    LoginMember loginMember = (LoginMember)session.getAttribute(SessionConst.LOGIN_MEMBER);
    //세션 정보가 없으면 로그인페이지로 이동
//    if(loginMember == null){
//      return "redirect:/login";
//    }
    String nickname = loginMember.getNickname();
    log.info("nickname={}", nickname);

    //세션에서 이메일,별칭가져오기
//    qna.setQEmail(loginMember.getEmail());
    qna.setQNickname(loginMember.getNickname());


    Long originId = qnaSVC.saveOrigin(qna);
    redirectAttributes.addAttribute("id", originId);
    // <=서버응답 302 get http://서버:port/bbs/10
    // =>클라이언트요청 get http://서버:port/bbs/10

    return "redirect:/qna/{id}";
  }
  //목록
  @GetMapping
  public String list(Model model) {

    List<QnaDTO> list = qnaSVC.findAll();

    List<QnaListForm> partOfList = new ArrayList<>();
    for (QnaDTO qna : list) {
      QnaListForm qnaListForm = new QnaListForm();
      BeanUtils.copyProperties(qna, qnaListForm);
      partOfList.add(qnaListForm);
    }

    model.addAttribute("list", partOfList);

    return "qna/qnaList";
  }
  //조회
  @GetMapping("/{id}")
  public String detail(
      @PathVariable Long id,
      Model model) {

    QnaDTO detailQna = qnaSVC.findByQNum(id);

    QnaDetailForm qnaDetailForm = new QnaDetailForm();

    BeanUtils.copyProperties(detailQna, qnaDetailForm);
    model.addAttribute("qnaDetailForm", qnaDetailForm);

    return "qna/qnaDetailForm";
  }
  //삭제
  @GetMapping("/{id}/del")
  public String del(@PathVariable long id) {

    qnaSVC.deleteByQNum(id);

    return "redirect:/qna";
  }
  //수정양식
  @GetMapping("/{id}/edit")
  public String editForm(@PathVariable Long id, Model model) {

    QnaDTO qna = qnaSVC.findByQNum(id);

    QnaEditForm qnaEditForm = new QnaEditForm();
    BeanUtils.copyProperties(qna,qnaEditForm);
    model.addAttribute("qnaEditForm",qnaEditForm);

    return "qna/qnaEditForm";
  }

  //수정처리
  @PostMapping("/{id}/edit")
  public String edit(@PathVariable Long id,
                     @Valid @ModelAttribute QnaEditForm qnaEditForm,
                     BindingResult bindingResult,
                     RedirectAttributes redirectAttributes) {

    if(bindingResult.hasErrors()) {
      return "qna/qnaEditForm";
    }

    QnaDTO qna = new QnaDTO();
    BeanUtils.copyProperties(qnaEditForm, qna);
    qnaSVC.updateByQNum(id, qna);

    redirectAttributes.addAttribute("id",id);

    return "redirect:/qna/{id}";
  }

  //답글작성양식
  @GetMapping("/{id}/reply")
  public String replyForm(@PathVariable Long id,
                          Model model, HttpSession session) {

    QnaDTO parentQna = qnaSVC.findByQNum(id);
    QnaReplyForm qnaReplyForm = new QnaReplyForm();
    qnaReplyForm.setQTitle("ㄴ답변:"+parentQna.getQTitle());

    //세션에서 로그인정보 가져오기
    LoginMember loginMember = (LoginMember) session.getAttribute("loginMember");//세션에서 로그인 정보 가져오기
//    LoginMember loginMember = (LoginMember)session.getAttribute(SessionConst.LOGIN_MEMBER);
//    qnaReplyForm.setQEmail(loginMember.getEmail());
    qnaReplyForm.setQNickname(loginMember.getNickname());

    model.addAttribute("qnaReplyForm", qnaReplyForm);
    return "qna/qnaReplyForm";
  }

  //답글작성처리
  @PostMapping("/{id}/reply")
  public String reply(
      @PathVariable Long id,  //부모글의 qNum
      @Valid QnaReplyForm qnaReplyForm,
      BindingResult bindingResult,
      RedirectAttributes redirectAttributes
  ) {

    if(bindingResult.hasErrors()) {
      return "qna/qnaReplyForm";
    }

    QnaDTO replyQna = new QnaDTO();
    BeanUtils.copyProperties(qnaReplyForm,replyQna);

    //부모글의 ,qNum,qgroup,qstep,qindent 참조
    appendInfoOfParentQna(id, replyQna);

    //답글저장(return 답글번호)
    Long replyQNum = qnaSVC.saveReply(id, replyQna);

    redirectAttributes.addAttribute("id",replyQNum);

    return "redirect:/qna/{id}";
  }

  //부모글의 QNum,qgroup,qstep,qindent
  private void appendInfoOfParentQna(Long parentQNum, QnaDTO replyQna) {
    QnaDTO parentQna = qnaSVC.findByQNum(parentQNum);
    replyQna.setPQNum(parentQna.getPQNum());
    replyQna.setQGroup(parentQna.getQGroup());
    replyQna.setQStep(parentQna.getQStep());
    replyQna.setQIndent(parentQna.getQIndent());
  }

}
