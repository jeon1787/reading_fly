package com.kh.reading_fly.domain.review.dao;

import com.kh.reading_fly.domain.review.dto.ReviewDTO;

import java.util.List;

public interface ReviewDAO {

  /**
   * 전체조회 by ISBN
   * @param risbn 도서번호
   * @return
   */
  List<ReviewDTO> selectAll(String risbn);

  /**
   * 단건조회 by rnum
   * @param rnum 리뷰번호
   * @return
   */
  ReviewDTO selectOne(Long rnum);

  /**
   * 리뷰 등록
   * @param reviewDTO
   * @return
   */
  ReviewDTO create(ReviewDTO reviewDTO);

  /**
   * 리뷰 수정
   * @param reviewDTO
   * @return
   */
  ReviewDTO update(ReviewDTO reviewDTO);

  /**
   * 리뷰 삭제
   * @param rnum
   * @param rid
   * @return
   */
  int delete(Long rnum, String rid);
}
