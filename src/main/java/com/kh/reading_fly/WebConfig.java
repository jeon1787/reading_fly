package com.kh.reading_fly;

import com.kh.reading_fly.web.interceptor.AdminCheckInterceptor;
import com.kh.reading_fly.web.interceptor.LoginCheckInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

  //cors허용하기위한 글로벌 설정
  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("/api/**")            //요청url
        .allowedOrigins("http://192.168.0.8:5500","http://localhost:5500")    //요청 client
        .allowedMethods("*")                            //모든 메소드
        .maxAge(3000);                                  //캐쉬시간
  }


  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(new LoginCheckInterceptor())
        .order(1)
        .addPathPatterns("/**")
        .addPathPatterns("/member/**")
        .addPathPatterns("/board/**")
        .addPathPatterns("/notices/**")


        .excludePathPatterns(
            "/",
            "/login",
            "/logout",
            "/signup",
            "/board",
            "/notices/all",
            "/css/**",
            "/js/**",
            "/img/**",
            "/member/findid",
            "/member/findpw"

        );


//    registry.addInterceptor(new AdminCheckInterceptor())
//        .order(2)
//        .addPathPatterns("/admin/**")
//        .addPathPatterns("/notices/")
//
//        .excludePathPatterns(
//
//        );


  }






}
