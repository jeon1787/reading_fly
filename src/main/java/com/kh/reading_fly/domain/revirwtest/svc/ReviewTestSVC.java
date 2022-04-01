package com.kh.reading_fly.domain.revirwtest.svc;


import com.kh.reading_fly.domain.review.dto.ReviewDTO;
import com.kh.reading_fly.domain.revirwtest.dto.ReviewReqDTO;
import com.kh.reading_fly.domain.revirwtest.dto.ReviewTestDTO;

import java.util.List;

public interface ReviewTestSVC {

  //리뷰등록
  List<ReviewReqDTO> registReview(ReviewTestDTO reviewTestDTO);

  //리뷰 전체 조회
  List<ReviewReqDTO> allReview(Long risbn);

  //리뷰 1개 호출(리뷰수정전 기존리뷰 호출)
  ReviewReqDTO findReview(int rnum);

  //리뷰수정
  List<ReviewReqDTO> updateReview(ReviewTestDTO reviewTestDTO);

  //리뷰삭제
  List<ReviewReqDTO> removeReview(Long risbn, int rnum);


}
