package com.kh.reading_fly.web.interceptor;

import com.kh.reading_fly.web.form.member.login.LoginMember;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Slf4j
public class AdminCheckInterceptor implements HandlerInterceptor {

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

//    String redirectUrl = null;
//    String requestURI = request.getRequestURI();
//    log.info("admin_requestURI={}", requestURI);
//
//    if (request.getQueryString() != null) {
//      String queryString = URLEncoder.encode(request.getQueryString(), "UTF-8");
//      StringBuffer str = new StringBuffer();
//      redirectUrl = str.append(requestURI)
//          .append("?")
//          .append(queryString)
//          .toString();
//    } else {
//      redirectUrl = requestURI;
//    }

    HttpSession session = request.getSession(false);

    LoginMember loginMember = (LoginMember) session.getAttribute("loginMember");
    int code =  loginMember.getAdmin_fl();

    if(code == 3) {
      log.info("admin 인증 요청");
//      response.sendRedirect("/redirectUrl=" + redirectUrl);
      response.sendRedirect("/");
      return false;
    }


      return true;

  }
}