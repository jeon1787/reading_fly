package com.kh.reading_fly.domain.calendar.dao;

import com.kh.reading_fly.domain.calendar.dto.CalendarDTO;
import com.kh.reading_fly.domain.comment.dto.CommentDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
public class CalendarDAOImpl implements CalendarDAO{

  private final JdbcTemplate jdbcTemplate;

  /**
   * id로 달력 조회
   * @param calendarDTO
   * @return
   */
  @Override
  public List<CalendarDTO> selectAll(CalendarDTO calendarDTO) {
    //sql 작성
    StringBuffer sql = new StringBuffer();
    sql.append(" select t1.thumbnail, t1.title, t3.ddate, t2.sid ");
    sql.append("   from book t1, book_shelf t2, ");
    sql.append("         SELECT ddate, did ");
    sql.append("           FROM document ");
    sql.append("          WHERE ddate >= TO_DATE( ? ,'YY/MM/DD') ");
    sql.append("            AND ddate <  TO_DATE( ? ,'YY/MM/DD')+1) t3 ");
    sql.append("  where t1.isbn = t2.sisbn ");
    sql.append("    and t2.sid = t3.did ");
    sql.append("    and t2.sid = ? ");
    sql.append("  order by t3.ddate; ");

    //sql 실행
    List<CalendarDTO> list = jdbcTemplate.query(
            sql.toString(),
            new BeanPropertyRowMapper<>(CalendarDTO.class),
            calendarDTO.getStartDate(),
            calendarDTO.getEndDate(),
            calendarDTO.getSid()
    );

    return list;
  }
}
