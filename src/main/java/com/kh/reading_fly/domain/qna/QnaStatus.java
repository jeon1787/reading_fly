package com.kh.reading_fly.domain.qna;

public enum QnaStatus {
  Q("원글"), A("답글");

  private final String description;

  QnaStatus(String description) {
    this.description = description;
  }

  public String getDescription() {
    return description;
  }
}

