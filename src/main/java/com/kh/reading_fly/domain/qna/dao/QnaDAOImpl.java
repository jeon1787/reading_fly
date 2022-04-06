package com.kh.reading_fly.domain.qna.dao;

import com.kh.reading_fly.domain.qna.QnaStatus;
import com.kh.reading_fly.domain.qna.dto.QnaDTO;
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
public class QnaDAOImpl implements QnaDAO {
  private  final JdbcTemplate jdbcTemplate;

  //원글작성
  @Override
  public Long saveOrigin(QnaDTO qna) {

    //SQL작성
    StringBuffer sql = new StringBuffer();
    sql.append("insert into qna (qnum,qtitle,qnickname,qcontent,qgroup) ");
    sql.append("values(qna_qnum_seq.nextval,?,?,?,qna_qnum_seq.currval) ");

    //SQL실행
    KeyHolder keyHolder = new GeneratedKeyHolder();
    jdbcTemplate.update(new PreparedStatementCreator() {
      @Override
      public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
        PreparedStatement pstmt = con.prepareStatement(
            sql.toString(),
            new String[]{"qnum"}  // insert 후 insert 레코드중 반환할 컬럼명, KeyHolder에 저장됨.
        );

        pstmt.setString(1, qna.getQTitle());
        pstmt.setString(2, qna.getQNickname());
        pstmt.setString(3, qna.getQContent());

        return pstmt;
      }
    },keyHolder);

    return Long.valueOf(keyHolder.getKeys().get("qnum").toString());
  }

  //목록
  @Override
  public List<QnaDTO> findAll() {
    StringBuffer sql = new StringBuffer();
    sql.append("SELECT " );
    sql.append(" qnum," );
    sql.append(" qtitle, " );
    sql.append(" qnickname, " );
    sql.append(" qhit, " );
    sql.append(" qcontent, " );
    sql.append(" pqnum, " );
    sql.append(" qgroup, " );
    sql.append(" qstep, " );
    sql.append(" qindent, " );
    sql.append(" qstatus, " );
    sql.append(" qcdate, " );
    sql.append(" qudate " ) ;
    sql.append("FROM ");
    sql.append(" qna ");
    sql.append("Order by qgroup desc, qstep asc ");

    List<QnaDTO> list = jdbcTemplate.query(
        sql.toString(), new BeanPropertyRowMapper<>(QnaDTO.class));

    return list;
  }

  @Override
  public List<QnaDTO> findAll(int startRec, int endRec) {
    StringBuffer sql = new StringBuffer();

    sql.append("select t1.* ");
    sql.append("from( ");
    sql.append("    SELECT ");
    sql.append("    ROW_NUMBER() OVER (ORDER BY qgroup DESC, qstep ASC) no, ");
    sql.append(" qnum," );
    sql.append(" qtitle, " );
    sql.append(" qnickname, " );
    sql.append(" qhit, " );
    sql.append(" qcontent, " );
    sql.append(" pqnum, " );
    sql.append(" qgroup, " );
    sql.append(" qstep, " );
    sql.append(" qindent, " );
    sql.append(" qstatus, " );
    sql.append(" qcdate, " );
    sql.append(" qudate " ) ;
    sql.append("    FROM qna) t1 ");
    sql.append("where t1.no between ? and ? ");

    List<QnaDTO> list = jdbcTemplate.query(
        sql.toString(),
        new BeanPropertyRowMapper<>(QnaDTO.class),
        startRec, endRec
    );
    return list;
  }

  //검색
  @Override
  public List<QnaDTO> findAll(QnaFilterCondition qnaFilterCondition) {
    StringBuffer sql = new StringBuffer();

    sql.append("select t1.* ");
    sql.append("from( ");
    sql.append("  SELECT ");
    sql.append("  ROW_NUMBER() OVER (ORDER BY qgroup DESC, qstep ASC) no, ");
    sql.append("      qnum, ");
    sql.append("      qtitle, ");
    sql.append("      qnickname, ");
    sql.append("      qhit, ");
    sql.append("      qcontent, ");
    sql.append("      pqnum, ");
    sql.append("      qgroup, ");
    sql.append("      qstep, ");
    sql.append("      qindent, ");
    sql.append("      qstatus, ");
    sql.append("      qcdate, ");
    sql.append("      qudate ");
    sql.append("  FROM qna ");
    sql.append("  WHERE ");

    //분류
    sql = dynamicQuery(qnaFilterCondition, sql);

    sql.append(") t1 ");
    sql.append("where t1.no between ? and ? ");

    List<QnaDTO> list = jdbcTemplate.query(
        sql.toString(),
        new BeanPropertyRowMapper<>(QnaDTO.class),
        qnaFilterCondition.getStartRec(),
        qnaFilterCondition.getEndRec()
    );

    return list;
  }

  //조회
  @Override
  public QnaDTO findByQNum(Long qNum) {
    StringBuffer sql = new StringBuffer();
    sql.append("SELECT " );
    sql.append(" qnum," );
    sql.append(" qtitle, " );
    sql.append(" qnickname, " );
    sql.append(" qhit, " );
    sql.append(" qcontent, " );
    sql.append(" pqnum, " );
    sql.append(" qgroup, " );
    sql.append(" qstep, " );
    sql.append(" qindent, " );
    sql.append(" qstatus, " );
    sql.append(" qcdate, " );
    sql.append(" qudate " ) ;
    sql.append("FROM ");
    sql.append(" qna ");
    sql.append("where qnum = ? ");

    QnaDTO qnaItem = null;
    try {
      qnaItem = jdbcTemplate.queryForObject(
          sql.toString(),
          new BeanPropertyRowMapper<>(QnaDTO.class),
          qNum);
    }catch (Exception e) {  // 1건을 못찾으면
      qnaItem = null;
    }

    return qnaItem;
  }

  //삭제
  @Override
  public int deleteByQNum(Long qNum) {
    StringBuffer sql = new StringBuffer();
    sql.append("DELETE FROM qna ");
    sql.append(" WHERE qnum = ? ");

    int updateItemCount = jdbcTemplate.update(sql.toString(), qNum);

    return updateItemCount;
  }

  //수정
  @Override
  public int updateByQNum(Long qNum, QnaDTO qna) {
    StringBuffer sql = new StringBuffer();
    sql.append("UPDATE qna" );
    sql.append("  SET qtitle = ?," );
    sql.append("    qcontent = ?," );
    sql.append("    qudate = systimestamp" );
    sql.append(" WHERE qnum = ?" );

    int updatedItemCount = jdbcTemplate.update(
        sql.toString(),
        qna.getQTitle(),
        qna.getQContent(),
        qNum
    );

    return updatedItemCount;
  }

  //답글
  @Override
  public Long saveReply(Long pQNum, QnaDTO replyQna) {

    //부모글 참조반영
    QnaDTO qna = addInfoOfParentToChild(pQNum,replyQna );

    StringBuffer sql = new StringBuffer();
    sql.append("insert into qna (qnum,qtitle,qnickname,qcontent,pqnum,qgroup,qstep,qindent) ");
    sql.append("values(qna_qnum_seq.nextval, ?, ? ,?,?,?,?,?) ");

    KeyHolder keyHolder = new GeneratedKeyHolder();
    jdbcTemplate.update(new PreparedStatementCreator() {
      @Override
      public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
        PreparedStatement pstmt = con.prepareStatement(
            sql.toString(),
            new String[]{"qnum"}  // insert 후 insert 레코드중 반환할 컬럼명, KeyHolder에 저장됨.
        );

        pstmt.setString(1, qna.getQTitle());
        pstmt.setString(2, qna.getQNickname());
        pstmt.setString(3, qna.getQContent());
        pstmt.setLong(4,qna.getPQNum());
        pstmt.setLong(5,qna.getQGroup());
        pstmt.setInt(6,qna.getQStep());
        pstmt.setInt(7,qna.getQIndent());
        return pstmt;
      }
    },keyHolder);

    return Long.valueOf(keyHolder.getKeys().get("qnum").toString());
  }

  //답글에 부모정보 반영하기
  private QnaDTO addInfoOfParentToChild(Long pQNum, QnaDTO replyQna) {
    //부모글
    QnaDTO qna = findByQNum(pQNum);

    //bgroup
    //답글의 bgroup = 부모글의 bgroup
    replyQna.setQGroup(qna.getQGroup());

    //step 로직
    //1) 부모글의 bgroup값과 동일한 게시글중 부모글의 step보다큰 게시글의 step을 1씩 증가
    int affectedRows = updateStep(qna);

    //2) 답글의 step값은 부모글의 step값 +1
    replyQna.setQStep(qna.getQStep()+1);

    //bindent 로직
    // 답글의 bindent = 부모글의 bindent +1
    replyQna.setQIndent(qna.getQIndent()+1);

    replyQna.setPQNum(pQNum);
    return  replyQna;
  }

  //부모글과 동일한그룹 step반영
  private int updateStep(QnaDTO qna) {
    StringBuffer sql = new StringBuffer();

    sql.append(" update qna ");
    sql.append(" set qstep = qstep + 1 ");
    sql.append(" where qgroup =  ? ");
    sql.append(" and qstep  >  ? ");

    int affectedRows = jdbcTemplate.update(sql.toString(), qna.getQGroup(), qna.getQStep());

    return affectedRows;

  }

  //조회수증가
  @Override
  public int increaseHitCount(Long qNum) {
    StringBuffer sql = new StringBuffer();
    sql.append("update qna ");
    sql.append("   set qhit = qhit + 1 ");
    sql.append(" where qnum = ? ");

    int affectedRows = jdbcTemplate.update(sql.toString(), qNum);

    return affectedRows;
  }

  //전체건수
  @Override
  public int totalCount() {

    String sql = "select count(*) from qna";

    Integer cnt = jdbcTemplate.queryForObject(sql, Integer.class);

    return cnt;
  }

  @Override
  public int totalCount(QnaFilterCondition filterCondition) {

    StringBuffer sql = new StringBuffer();

    sql.append("select count(*) ");
    sql.append("  from qna  ");
    sql.append(" where  ");

    sql = dynamicQuery(filterCondition, sql);

    Integer cnt = 0;

    cnt = jdbcTemplate.queryForObject(
        sql.toString(), Integer.class
    );

    return cnt;
  }

  @Override
  public int updateStatus(Long qNum) {
    StringBuffer sql = new StringBuffer();
    sql.append("update qna ");
    sql.append("   set qStatus = 'A' ");
    sql.append(" where qNum = ? ");

    int cnt = jdbcTemplate.update(sql.toString(), qNum);

    return cnt;
  }

  private StringBuffer dynamicQuery(QnaFilterCondition filterCondition, StringBuffer sql) {

    //검색유형
    switch (filterCondition.getSearchType()){
      case "TC":  //제목 + 내용
        sql.append("    (  qtitle    like '%"+ filterCondition.getKeyword()+"%' ");
        sql.append("    or qcontent like '%"+ filterCondition.getKeyword()+"%' )");
        break;
      case "T":   //제목
        sql.append("       qtitle    like '%"+ filterCondition.getKeyword()+"%' ");
        break;
      case "C":   //내용
        sql.append("       qcontent like '%"+ filterCondition.getKeyword()+"%' ");
        break;
      case "N":   //별칭
        sql.append("       qnickname like '%"+ filterCondition.getKeyword()+"%' ");
        break;
      default:
    }
    return sql;
  }
}
