package com.kh.reading_fly.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/map")
public class MapController {

  public String map(){
    log.info("map() 호출됨!");

    return "map/map";
  }
}
