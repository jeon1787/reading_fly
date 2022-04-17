package com.kh.reading_fly.web.form.calendar;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CalForm {
  String startDate;
  String endDate;
  String sid;
}
