package com.kh.reading_fly.web.controller;

import com.kh.reading_fly.domain.member.svc.MemberSVC;
import com.kh.reading_fly.web.form.support.FindIdReq;
import com.kh.reading_fly.web.form.support.FindPwReq;
import com.kh.reading_fly.web.form.support.JsonResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.util.StringUtils;

@Slf4j
@Controller
@RequiredArgsConstructor
public class RestSupportController {

  private final MemberSVC memberSVC;


  // id 찾기 확인
  @ResponseBody
  @PutMapping("/api/findid")
  public JsonResult<String> findIdMember(
          @RequestBody FindIdReq findIdReq, BindingResult bindingResult){

    log.info("findIdReq={}",findIdReq);
    JsonResult<String> result = null;

    String id = memberSVC.findIdMember(findIdReq.getEmail(),findIdReq.getName());

    if(!StringUtils.isEmpty(id)) {
      result = new JsonResult<>("00", "success", id);

    }else{
      result = new JsonResult<>("99", "fail", "찾고자하는 아이디가 없습니다.");

    }
    return result;
  }

  // pw 찾기 획인
  @ResponseBody
  @PutMapping("/api/findpw")
  public JsonResult<String> findPwMember(
          @RequestBody FindPwReq findPwReq, BindingResult bindingResult){

    log.info("findPwReq={}",findPwReq);
    JsonResult<String> result = null;

    String pw = memberSVC.findPwMember(findPwReq.getId(),findPwReq.getName(), findPwReq.getEmail());

    if(!StringUtils.isEmpty(pw)) {
      result = new JsonResult<>("00", "success", pw);

    }else{
      result = new JsonResult<>("99", "fail", "찾고자하는 비밀번호가 없습니다.");

    }
    return result;
  }






}
