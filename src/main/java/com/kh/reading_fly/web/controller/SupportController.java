package com.kh.reading_fly.web.controller;

import com.kh.reading_fly.domain.member.svc.MemberSVC;
import com.kh.reading_fly.web.form.member.FindIdReq;
import com.kh.reading_fly.web.form.member.FindPwReq;
import com.kh.reading_fly.web.form.support.JsonResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


@Slf4j
@Controller
//@RestController
//@AllArgsConstructor
@RequiredArgsConstructor
@RequestMapping("/support")
public class SupportController {

  private final MemberSVC memberSVC;










  //아이디 중복체크  -- 어딜가든 형식 사용할 경우
  @GetMapping("/id/dupchk")
  public JsonResult<String> dupChkId(
          @RequestParam String id){
    JsonResult<String> result = null;
    if(memberSVC.isExistId(id)) {
      result = new JsonResult<String>("00", "OK", id);
    }else {
      result = new JsonResult<String>("01", "NOK", null);
    }
    return result;
  }

  //이메일 중복체크 -- 어딜가든 형식 사용할 경우
  @GetMapping("/email/dupchk")
  public JsonResult<String> dupChkEmail(
          @RequestParam String email){
    JsonResult<String> result = null;
    if(memberSVC.isExistEmail(email)) {
      result = new JsonResult<String>("00", "OK", email);
    }else {
      result = new JsonResult<String>("01", "NOK", null);
    }
    return result;
  }

  //닉네임 중복체크 -- 어딜가든 형식 사용할 경우
  @GetMapping("/nickname/dupchk")
  public JsonResult<String> dupChkNickname(
          @RequestParam String nickname){
    JsonResult<String> result = null;
    if(memberSVC.isExistNickname(nickname)) {
      result = new JsonResult<String>("00", "OK", nickname);
    }else {
      result = new JsonResult<String>("01", "NOK", null);
    }
    return result;
  }







  //아이디 찾기
  @PostMapping("/findida")
  public JsonResult<String> findId(
          @RequestBody FindIdReq findIdReq, BindingResult bindingResult ) {
    log.info("findIdReq:{}",findIdReq);
    if(bindingResult.hasErrors()) {
      return null;
    }
    String findedId =
            memberSVC.findIdMember(findIdReq.getEmail(),findIdReq.getName());
    return new JsonResult<String>("00","ok",findedId);
  }

  //비밀번호 찾기
  @PostMapping("/findpwa")
  public JsonResult<String> findPw(
          @RequestBody FindPwReq findPwReq,
          BindingResult bindingResult
  ) {

    log.info("FindPwReq:{}",findPwReq);
    if(bindingResult.hasErrors()) {
      return null;
    }

    String findedPw =
            memberSVC.findPwMember(
                    findPwReq.getId(),findPwReq.getName(), findPwReq.getEmail());

    return new JsonResult<String>("00","ok",findedPw);
  }




}
