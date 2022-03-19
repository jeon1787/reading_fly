package com.kh.reading_fly.domain.qna;

public enum QnaStatus {
  D("삭제"), I("임시저장"), W("경고");

  private final String description;

  QnaStatus(String description) {
    this.description = description;
  }

  public String getDescription() {
    return description;
  }
}

