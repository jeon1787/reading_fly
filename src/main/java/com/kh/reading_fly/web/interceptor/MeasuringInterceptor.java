package com.kh.reading_fly.web.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Slf4j
public class MeasuringInterceptor implements HandlerInterceptor {

  public static final String LOG_ID= "logId";

  //컨트롤러 호출전에 실행
  //반환값이 true: 다음 인터셉터 혹은 컨트롤러 실행
  //       false : 컨트롤러 실행중지됨.
  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    log.info("MeasuringInterceptor.preHandle");

    String requestURI = request.getRequestURI();
    String uuid = UUID.randomUUID().toString();

    request.setAttribute(LOG_ID	, uuid);
    request.setAttribute("beginTime", System.currentTimeMillis());

    if(handler instanceof HandlerMethod) {
      HandlerMethod hm = (HandlerMethod)handler;  //호출할 컨트롤러 메서드의 모든 정보를 갖고있음.
    }

    log.info("Request [{}][{}][{}]", uuid, requestURI, handler);
    return true;
  }

  //컨트롤서 실행후 호출됨
  @Override
  public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                         ModelAndView modelAndView) throws Exception {
    log.info("MeasuringInterceptor.postHandle");

    String uuid = (String) request.getAttribute(LOG_ID);
    log.info("Request [{}][{}][{}]", uuid, modelAndView);
  }

  //뷰가 렌더링되고 클라이언트 응답후 호출됨.
  @Override
  public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
      throws Exception {
    log.info("MeasuringInterceptor.afterCompletion");
    String requestURI = request.getRequestURI();
    String uuid = (String) request.getAttribute(LOG_ID);

    long beginTime = (long)request.getAttribute("beginTime");
    long endTime = System.currentTimeMillis();

    log.info("Response [{}][{}][실행시간:{}][{}]",uuid, requestURI, (endTime-beginTime),handler);

    if(ex != null) {
      log.error("afterCompletion error!!", ex);
    }
  }
}
