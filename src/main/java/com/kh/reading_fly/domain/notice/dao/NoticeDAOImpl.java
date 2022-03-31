package com.kh.reading_fly.domain.notice.dao;

import com.kh.reading_fly.domain.notice.dto.NoticeDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
public class NoticeDAOImpl implements NoticeDAO{

  private final JdbcTemplate jdbcTemplate;

  /**
   * 등록
   * @param notice
   * @return
   */
  @Override
  public Long create(NoticeDTO notice) {
    //SQL작성
    StringBuffer sql = new StringBuffer();
    sql.append("insert into notice (nnum,ntitle,ncontent) ");
    sql.append("values(notice_nnum_seq.nextval, ?, ? ) ");

    //SQL실행
    KeyHolder keyHolder = new GeneratedKeyHolder();
    jdbcTemplate.update(new PreparedStatementCreator() {

      @Override
      public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
        PreparedStatement pstmt = con.prepareStatement(
            sql.toString(),
            new String[]{"nnum"}  // insert 후 insert 레코드중 반환할 컬럼명, KeyHolder에 저장됨.
        );

        pstmt.setString(1, notice.getNTitle());
        pstmt.setString(2, notice.getNContent());

        return pstmt;
      }
    },keyHolder);

    return Long.valueOf(keyHolder.getKeys().get("nnum").toString());
  }

  /**
   * 전제조회
   * @return
   */
  @Override
  public List<NoticeDTO> findAll() {

    StringBuffer sql = new StringBuffer();
    sql.append("select nnum, ntitle, ncontent, nhit, ncdate, nudate ");
    sql.append("  from notice ");
    sql.append("order by nnum desc ");

    List<NoticeDTO> list = jdbcTemplate.query(
        sql.toString(), new BeanPropertyRowMapper<>(NoticeDTO.class));

    return list;
  }

  @Override
  public List<NoticeDTO> findAll(int startRec, int endRec) {
    StringBuffer sql = new StringBuffer();

    sql.append("select t1.* ");
    sql.append("from( ");
    sql.append("select ");
    sql.append("ROW_NUMBER() OVER(ORDER BY nnum desc)no, ");
    sql.append("nnum,ntitle,ncontent,nhit,ncdate,nudate ");
    sql.append("from notice) t1 ");
    sql.append("where t1.no between ? and ? ");


    List<NoticeDTO> list = jdbcTemplate.query(
        sql.toString(),
        new BeanPropertyRowMapper<>(NoticeDTO.class),
        startRec, endRec
    );
    return list;
  }

  /**
   * 상세조회
   * @param nNum
   * @return
   */
  @Override
  public NoticeDTO selectOne(Long nNum) {

    StringBuffer sql = new StringBuffer();
    sql.append("select nnum, ntitle,ncontent, nhit, ncdate, nudate ");
    sql.append("from notice ");
    sql.append("where nnum = ? ");

    List<NoticeDTO> query = jdbcTemplate.query(
        sql.toString(), new BeanPropertyRowMapper<>(NoticeDTO.class), nNum);

    return (query.size() == 1) ? query.get(0) : null;
  }

  /**
   * 수정
   * @param notice
   * @return
   */
  @Override
  public NoticeDTO update(NoticeDTO notice) {

    StringBuffer sql = new StringBuffer();
    sql.append("update notice ");
    sql.append("set ntitle = ? , ");
    sql.append("    ncontent = ? , ");
    sql.append("    nudate   = systimestamp ");
    sql.append("where nnum = ? ");

    KeyHolder keyHolder = new GeneratedKeyHolder();
    jdbcTemplate.update(new PreparedStatementCreator() {
      @Override
      public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
        PreparedStatement pstmt = con.prepareStatement(
            sql.toString(),
            new String[]{"nnum"}  // update 후 update 레코드중 반환할 컬럼명, KeyHolder에 저장됨.
        );

        pstmt.setString(1, notice.getNTitle());
        pstmt.setString(2, notice.getNContent());
        pstmt.setLong(3, notice.getNNum());

        return pstmt;
      }
    },keyHolder);

    long nNum = Long.valueOf(keyHolder.getKeys().get("nnum").toString());
    return selectOne(nNum);
  }

  /**
   * 삭제
   * @param nNum
   * @return
   */
  @Override
  public int delete(Long nNum) {

    StringBuffer sql = new StringBuffer();
    sql.append("delete from notice ");
    sql.append(" where nnum = ? ");

    int cnt = jdbcTemplate.update(sql.toString(), nNum);

    return cnt;
  }

  /**
   * 조회수 증가
   * @param nNum
   * @return
   */
  @Override
  public int updateHit(Long nNum) {

    StringBuffer sql = new StringBuffer();
    sql.append("update notice ");
    sql.append("   set nhit = nhit + 1 ");
    sql.append(" where nnum = ? ");

    int cnt = jdbcTemplate.update(sql.toString(), nNum);

    return cnt;
  }

  //전체건수
  @Override
  public int totalCount() {

    String sql = "select count(*) from notice";

    Integer cnt = jdbcTemplate.queryForObject(sql, Integer.class);

    return cnt;
  }
}