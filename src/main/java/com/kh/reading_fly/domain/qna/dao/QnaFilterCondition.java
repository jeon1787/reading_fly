package com.kh.reading_fly.domain.qna.dao;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
public class QnaFilterCondition {
  private int startRec;         //시작레코드번호
  private int endRec;           //종료레코드번호
  private String searchType;    //검색유형
  private String keyword;       //검색어

  public QnaFilterCondition(String searchType, String keyword) {
    this.searchType = searchType;
    this.keyword = keyword;
  }
}
