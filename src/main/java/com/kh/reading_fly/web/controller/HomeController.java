package com.kh.reading_fly.web.controller;


import com.kh.reading_fly.web.form.login.LoginMember;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Slf4j
@Controller
public class HomeController {

  @RequestMapping("/")
  public String home(HttpServletRequest request){

    log.info("info={}","home()호출됨");

    String view = null;
    HttpSession session = request.getSession(false);

    view = (session == null || session.getAttribute("loginMember") == null) ? "main/main" : "main/mainDetail" ;

    return view;
  }
}