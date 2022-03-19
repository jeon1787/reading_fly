package com.kh.reading_fly.web.interceptor;


import com.kh.reading_fly.domain.member.dto.MemberDTO;
import com.kh.reading_fly.domain.member.svc.MemberSVC;
import com.kh.reading_fly.web.form.login.LoginMember;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.net.URLEncoder;

@Slf4j
public class AdminCheckInterceptor implements HandlerInterceptor{

  private MemberSVC memberSVC;
  private MemberDTO memberDTO;


  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    String redirectUrl = null;
    String requestURI = request.getRequestURI();
    log.info("admin_requestURI={}", requestURI);

    if(request.getQueryString() != null){
      String queryString = URLEncoder.encode(request.getQueryString(), "UTF-8");
      StringBuffer str = new StringBuffer();
      redirectUrl = str.append(requestURI)
          .append("?")
          .append(queryString)
          .toString();
    }else{
      redirectUrl = requestURI;
    }



    LoginMember loginMember = new LoginMember(
        memberDTO.getId(), memberDTO.getNickname(), memberDTO.getEmail(), memberDTO.getAdmin_fl());
    log.info("id={}, email={}, nickname={}, admin_fl={}",
        memberDTO.getId(), memberDTO.getEmail(),memberDTO.getNickname(), memberDTO.getAdmin_fl());
    HttpSession session = request.getSession(true);
    session.setAttribute("loginMember", loginMember );

    int code = loginMember.getAdmin_fl();
//    int code = Integer.parseInt(memberSVC.admin(loginMember.getId()));
    if(code == 2) {
      log.info("admin 인증 요청");
      response.sendRedirect("/redirectUrl=" + redirectUrl);
      return false;
    }




//    LoginMember loginMember = (LoginMember) session.getAttribute("loginMember");
//    int code = Integer.parseInt(memberSVC.admin(loginMember.getId()));
//    if(code == 2) {
//      log.info("admin 인증 요청");
//      response.sendRedirect("/redirectUrl=" + redirectUrl);
//      return false;
//    }



//    if(session == null || session.getAttribute("loginMember.admin_fl") == "3" ){
//      if(session.getAttribute("loginMember.admin_fl()") == "2" ){
//      log.info("미인증 요청");
//      response.sendRedirect("/redirectUrl=" + redirectUrl);
//      return false;
//    }

    return true;
  }


}
