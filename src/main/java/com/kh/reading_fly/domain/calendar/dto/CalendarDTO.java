package com.kh.reading_fly.domain.calendar.dto;


import lombok.*;
import org.apache.tomcat.jni.Local;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CalendarDTO {
  LocalDate startDate;
  LocalDate endDate;
  String sid;
  String thumbnail;
  String title;
  LocalDate ddate;

  String dnum;
}
