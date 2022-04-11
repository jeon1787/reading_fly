package com.kh.reading_fly.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping
public class CalendarController {

  @GetMapping("/calendar")
  public  String calendar(){
    return "calendar/calendar";
  }
}
