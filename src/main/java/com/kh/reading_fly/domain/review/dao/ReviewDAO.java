package com.kh.reading_fly.domain.review.dao;

import com.kh.reading_fly.domain.review.dto.ReviewDTO;

import java.util.List;

public interface ReviewDAO {
  
  //목록, 단건, 삭제, 등록, 수정

  /**
   * 전체조회 by ISBN
   * @param risbn 도서번호
   * @return
   */
  List<ReviewDTO> reSelectAll(Long risbn);

  /**
   * 단건조회 by rnum
   * @param rnum 리뷰번호
   * @return
   */
  ReviewDTO reSelectOne(Long rnum);

  // 리뷰 등록
  ReviewDTO reCreate(ReviewDTO reviewDTO);

  // 리뷰 수정
  ReviewDTO reUpdate(ReviewDTO reviewDTO);

  // 리뷰 삭제
  int reDelete(Long rnum, String rid);


}
