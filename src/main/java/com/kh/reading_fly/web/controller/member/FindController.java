package com.kh.reading_fly.web.controller.member;

import com.kh.reading_fly.domain.member.dto.MemberDTO;
import com.kh.reading_fly.domain.member.svc.MemberSVC;
import com.kh.reading_fly.web.controller.member.pwutil.PasswordGeneratorCreator;
import com.kh.reading_fly.web.form.member.find.ChangPwReq;
import com.kh.reading_fly.web.form.member.find.FindIdReq;
import com.kh.reading_fly.web.form.member.find.FindPwReq;
import com.kh.reading_fly.web.form.member.support.JsonResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;


@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/find")
public class FindController {

  private final MemberSVC memberSVC;


  //아이디 및 pw 찾기
  @GetMapping("/findidpw")
  public String findidpw(
      @ModelAttribute FindIdReq findIdReq,
      @ModelAttribute FindPwReq findPwReq,
      BindingResult bindingResult
  ) {
    log.info("findIdPwForm() 호출됨!");
    return "member/find/findIdPwForm";
  }


  @GetMapping("/findida")
  public String FindIdReq(@ModelAttribute FindIdReq findIdReq,
                          BindingResult bindingResult) {
    return "member/find/t/findIdForm";
  }


  @PostMapping("/findida")
  public String findId(@Valid @ModelAttribute FindIdReq findIdReq,
                       BindingResult bindingResult,
                       Model model,
                       HttpServletRequest request, HttpServletResponse response
  ) {

    if (bindingResult.hasErrors()) {
      log.info("bindingResult={}", bindingResult);
//      return "/member/find/findIdPwForm";
      return "redirect:/find/findidpw";
    }


    String fId = memberSVC.findIdMember(findIdReq.getIdemail(), findIdReq.getIdname());

    if (fId == null) {
      bindingResult.reject("error.findida", "회원정보가 없습니다");
//      return "redirect:/find/findidpw";
      return "member/find/findIdNullResult";
//      return "/member/find/findIdPwForm";      // 에러 : Neither BindingResult nor plain target object for bean name 'findPwReq' available as request attribute

    }

//    if(!memberSVC.isExistId(fId)) {
//      log.info("findidaError={}", bindingResult);
//      bindingResult.reject("error.login", "회원정보가 없습니다");
//      return "redirect:/find/findidpw";
////      return "member/find/findIdPwForm";
//    }

    if (memberSVC.isDeleteId(fId)) {
      bindingResult.reject("error.findida", "이며 탈퇴된 회원입니다");
//      model.addAttribute("findIdReq",findIdReq);
      model.addAttribute("fId", fId);
      return "member/find/findIdDelResult";
//      return "member/find/findIdPwForm";
    }


//
//    if(delId == null){
//      return "redirect:/find/findidpw";
//    }else if(memberSVC.isDeleteId(delId)) {
//      model.addAttribute("delId",delId);
//      return "member/find/findIdDelResult";
//    }

    List<String> findids = memberSVC.findMemberId(findIdReq);
    model.addAttribute("findids", findids);
    return "member/find/findIdResult";
//    return "member/find/t/findedIdResult";
  }

  @GetMapping("/findpwa")
  public String FindPwReq(@ModelAttribute FindPwReq findPwReq,
                          BindingResult bindingResult) {
    return "member/find/t/findPWForm";
  }

  @PostMapping("/findpwa")
  public String findPW(@Valid @ModelAttribute FindPwReq findPwReq,
                       BindingResult bindingResult,
                       Model model,
                       HttpServletRequest request, HttpServletResponse response
  ) {

    if (bindingResult.hasErrors()) {
      log.info("bindingResult={}", bindingResult);
//      return "/member/find/findIdPwForm";
      return "redirect:/find/findidpw";
    }


    String fId = memberSVC.findIdMember(findPwReq.getPwemail(), findPwReq.getPwname());

    if (fId == null) {
      bindingResult.reject("error.findida", "회원정보가 없습니다");
//      return "redirect:/find/findidpw";
      return "member/find/findIdNullResult";
//      return "/member/find/findIdPwForm";      // 에러 : Neither BindingResult nor plain target object for bean name 'findPwReq' available as request attribute

    }

//    if(!memberSVC.isExistId(fId)) {
//      log.info("findidaError={}", bindingResult);
//      bindingResult.reject("error.login", "회원정보가 없습니다");
//      return "redirect:/find/findidpw";
////      return "member/find/findIdPwForm";
//    }

    if (memberSVC.isDeleteId(fId)) {
      bindingResult.reject("error.findida", "이며 탈퇴된 회원입니다");
//      model.addAttribute("findIdReq",findIdReq);
      model.addAttribute("fId", fId);
      return "member/find/findIdDelResult";
//      return "member/find/findIdPwForm";
    }


    ChangPwReq changPwReq = memberSVC.findMemberPw(findPwReq);

    MemberDTO memberDTO = memberSVC.findByID(findPwReq.getPwid());

    String tmpPw = PasswordGeneratorCreator.generator(10);

    memberSVC.changeMemberPW(findPwReq.getPwemail(), changPwReq.getPw(), tmpPw);

    List<String> findpws = memberSVC.changeMemberPW(findPwReq);
    model.addAttribute("findpws", findpws);
    return "member/find/findPwResult";
//
//
//
//
//
//
//    model.addAttribute("findpws", findpws);
//    return "member/findedId_Result";

  }



}
