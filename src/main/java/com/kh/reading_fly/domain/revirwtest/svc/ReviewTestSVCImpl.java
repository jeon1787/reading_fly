package com.kh.reading_fly.domain.revirwtest.svc;

import com.kh.reading_fly.domain.review.dto.ReviewDTO;
import com.kh.reading_fly.domain.revirwtest.dao.ReviewTestDAO;
import com.kh.reading_fly.domain.revirwtest.dto.ReviewReqDTO;
import com.kh.reading_fly.domain.revirwtest.dto.ReviewTestDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReviewTestSVCImpl implements ReviewTestSVC{

  private final ReviewTestDAO reviewTestDAO;


  @Override
  public List<ReviewReqDTO> registReview(ReviewTestDTO reviewTestDTO) {
//    int rnum = reviewTestDAO.registReview(reviewTestDTO);
//    return allReview(reviewTestDTO.getRisbn());

    Long risbn = Long.valueOf(reviewTestDAO.registReview(reviewTestDTO));
    return allReview(risbn);
  }

  @Override
  public List<ReviewReqDTO> allReview(Long risbn) {
    List<ReviewReqDTO> list = reviewTestDAO.allReview(risbn);
    return list;
  }

  @Override
  public ReviewReqDTO findReview(int rnum) {
    ReviewReqDTO review = reviewTestDAO.findReview(rnum);
    return review;
  }

  @Override
  public List<ReviewReqDTO> updateReview(ReviewTestDTO reviewTestDTO) {
    return allReview(reviewTestDTO.getRisbn());
  }

  @Override
  public List<ReviewReqDTO> removeReview(Long risbn, int rnum) {
    return allReview(risbn);
  }

}
