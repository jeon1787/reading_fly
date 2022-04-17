package com.kh.reading_fly.domain.calendar.svc;

import com.kh.reading_fly.domain.calendar.dao.CalendarDAO;
import com.kh.reading_fly.domain.calendar.dto.CalendarDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CalendarSVCImpl implements CalendarSVC{

  private final CalendarDAO calendarDAO;

  /**
   * id로 달력 조회
   * @param calendarDTO
   * @return
   */
  @Override
  public List<CalendarDTO> selectAll(CalendarDTO calendarDTO) {
    return calendarDAO.selectAll(calendarDTO);
  }
}
