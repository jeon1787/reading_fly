package com.kh.reading_fly.domain.calendar.svc;

import com.kh.reading_fly.domain.calendar.dto.CalendarDTO;

import java.util.List;

public interface CalendarSVC {
  /**
   * id로 달력 조회
   * @param calendarDTO
   * @return
   */
  List<CalendarDTO> selectAll(CalendarDTO calendarDTO);
}
