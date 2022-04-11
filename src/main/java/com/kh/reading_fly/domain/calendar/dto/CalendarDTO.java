package com.kh.reading_fly.domain.calendar.dto;


import lombok.*;

import java.sql.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CalendarDTO {
  Date startDate;
  Date endDate;
  String sid;
}
