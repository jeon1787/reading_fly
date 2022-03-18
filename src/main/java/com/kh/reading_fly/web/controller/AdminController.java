package com.kh.reading_fly.web.controller;

import com.kh.reading_fly.domain.member.dto.MemberDTO;
import com.kh.reading_fly.domain.member.svc.MemberSVC;
import com.kh.reading_fly.web.form.login.LoginMember;
import com.kh.reading_fly.web.form.member.MemberDetailForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

  private final MemberSVC memberSVC;

  //관리자 홈
  @GetMapping
  public String home(){
    return "member/adminForm";
  }

  //회원전체조회
  @GetMapping("/memberlist")
  public String memberlist(Model model){

    List<MemberDTO> memberAll = memberSVC.allMemberList();
    model.addAttribute("memberAll", memberAll);

    return "member/adminMemberList";


  }

  //회원개별조회
  @GetMapping("/member/{id}")
  public String member(
      @PathVariable("id") String id,
      Model model){

    MemberDTO memberDTO = memberSVC.findByID(id);
    log.info("memberDTO={}", memberDTO);

    MemberDetailForm memberdetailForm = new MemberDetailForm();
    memberdetailForm.setId(memberDTO.getId());
    memberdetailForm.setName(memberDTO.getName());
    memberdetailForm.setEmail(memberDTO.getEmail());
    memberdetailForm.setNickname(memberDTO.getNickname());
    log.info("memberdetailForm={}", memberdetailForm);
    model.addAttribute("memberdetailForm", memberdetailForm);

    return "member/adminMemberDetail";
  }

//  @GetMapping("/memberdetail")
//  public String memberid(
//      @PathVariable("id") String id,
//      HttpServletRequest request,
//      Model model){
//
//
//    HttpSession session = request.getSession(false);
//    LoginMember loginMember
//        = (LoginMember)session.getAttribute("loginMember");
//
//    MemberDTO memberDTO = memberSVC.findByID(loginMember.getId());
//    log.info("memberDTO={}", memberDTO);
//
//    MemberDetailForm memberdetailForm = new MemberDetailForm();
//    BeanUtils.copyProperties(memberDTO, memberdetailForm);
//    log.info("memberdetailForm={}", memberdetailForm);
//    model.addAttribute("memberdetailForm", memberdetailForm);
//
//    return "member/adminMemberDetail";
//  }
//







}
