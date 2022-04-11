package com.kh.reading_fly.domain.review.svc;

import com.kh.reading_fly.domain.review.dto.ReviewDTO;

import java.util.List;

public interface ReviewSVC {

  // isbn 번호로 리뷰 전체 조회
  List<ReviewDTO> selectAll(String risbn);

  // 리뷰 수정의 취소 경우 화면 조회
  ReviewDTO selectOne(Long rnum);

  // 리뷰 등록
  ReviewDTO create(ReviewDTO reviewDTO);

  // 리뷰 수정
  ReviewDTO update(ReviewDTO reviewDTO);

  // 리뷰 삭제
  int delete(Long rnum, String rid);



}
