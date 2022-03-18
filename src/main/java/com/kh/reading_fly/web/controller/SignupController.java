package com.kh.reading_fly.web.controller;

import com.kh.reading_fly.domain.member.dto.MemberDTO;
import com.kh.reading_fly.domain.member.svc.MemberSVC;
import com.kh.reading_fly.web.form.signup.SignupForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


import javax.validation.Valid;


@Slf4j
@Controller
@RequiredArgsConstructor
public class SignupController {

  private final MemberSVC memberSVC;

  //회원가입
  @GetMapping("/signup")
  public String signupForm(@ModelAttribute SignupForm signupForm){
    log.info("signupForm() 호출됨!");
    return "signup/signupForm";
  }

  //회원가입처리
  @PostMapping("/signup")
  public String signup(@Valid @ModelAttribute SignupForm signupForm,
                       BindingResult bindingResult){

    log.info("signup() 호출됨!");
    log.info("id={}, pw={}, pwChk={}, name={}, email={}, nickname={}",
            signupForm.getId(), signupForm.getPw(), signupForm.getPwChk(),
            signupForm.getName(), signupForm.getEmail(),signupForm.getNickname());

    //1)유효성체크 - 필드오류
    if(bindingResult.hasErrors()){
      log.info("error={}", bindingResult);
      return "signup/signupForm";
    }
    //회원 존재유무
    if(memberSVC.isExistId(signupForm.getId())) {
      bindingResult.rejectValue("id","id", "동일한 아이디가 존재합니다");
      return "signup/signupForm";
    }

    //탈퇴된 회원 유무
    if(memberSVC.isDeleteId(signupForm.getId())) {
      bindingResult.rejectValue("delid","delid", "이미 삭제된 회원의 아이디입니다");
      return "signup/signupForm";
    }

    //비밀번호 확인 체크
    if(!signupForm.getPw().equals(signupForm.getPwChk())) {
      bindingResult.rejectValue("PwChk", "PwChk", "비밀번호가 일치하지 않습니다.");
      return "signup/signupForm";
    }

    //email 존재유무
    if(memberSVC.isExistEmail(signupForm.getEmail())) {
      bindingResult.rejectValue("email","email", "동일한 email이 존재합니다");
      return "signup/signupForm";
    }

    //닉네임 존재유무
    if(memberSVC.isExistNickname(signupForm.getNickname())) {
      bindingResult.rejectValue("nickname","nickname", "동일한 닉네임이 존재합니다");
      return "signup/signupForm";
    }

    //4)정상처리로직
    log.info("signupForm={}", signupForm);
//    MemberDTO memberDTO = new MemberDTO( signupForm.getId(), signupForm.getEmail(),
//            signupForm.getPw(), signupForm.getName(), signupForm.getNickname());
//    MemberDTO memberDTO = new MemberDTO( signupForm.getId(), signupForm.getEmail(),
//        signupForm.getPw(), signupForm.getName(), signupForm.getNickname(),
//        signupForm.getSignup_dt(), signupForm.getLeave_fl(), signupForm.getLeave_dt());

    MemberDTO memberDTO = new MemberDTO();
    BeanUtils.copyProperties(signupForm, memberDTO);
    memberSVC.insertMember(memberDTO);
    log.info("id={}, pw={}, name={}, email={}, nickname={}",
            signupForm.getId(), signupForm.getPw(), signupForm.getEmail(),signupForm.getNickname());

    return "redirect:/signupSuccess";
  }

  @GetMapping("/signupSuccess")
  public String joinSuccess(){
    return "signup/signupSuccess";
  }



}
