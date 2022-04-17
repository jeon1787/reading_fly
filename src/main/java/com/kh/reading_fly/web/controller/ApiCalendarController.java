package com.kh.reading_fly.web.controller;

import com.kh.reading_fly.domain.calendar.dto.CalendarDTO;
import com.kh.reading_fly.domain.calendar.svc.CalendarSVC;
import com.kh.reading_fly.web.form.calendar.CalForm;
import com.kh.reading_fly.web.form.comment.ApiResult;
import com.kh.reading_fly.web.form.member.login.LoginMember;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/calendar")
public class ApiCalendarController {

  private  final CalendarSVC calendarSVC;

  @PostMapping
  public ApiResult<Object> calendar(@RequestBody CalForm calForm,
                                    HttpSession session){
    log.info("달력 ApiController 실행됨");
    log.info("calForm={}",calForm);
    
    LoginMember loginMember = (LoginMember) session.getAttribute("loginMember");//세션에서 로그인 정보 가져오기
    calForm.setSid(loginMember.getId());

    CalendarDTO calendarDTO = new CalendarDTO();
    calendarDTO.setSid(calForm.getSid());
    calendarDTO.setStartDate(transToLocalDate(calForm.getStartDate()));
    calendarDTO.setEndDate(transToLocalDate(calForm.getEndDate()));
    List<CalendarDTO> list = calendarSVC.selectAll(calendarDTO);

    return  new ApiResult<>("00", "success", list);
  }

  private LocalDate transToLocalDate(String date) {
    String[] dates = date.split("/");
    LocalDate transtedDate = LocalDate.of(
            Integer.valueOf(dates[0]),
            Integer.valueOf(dates[1]),
            Integer.valueOf(dates[2]));
    log.info("transtedDate={}",transtedDate);
    return transtedDate;
  }
}
