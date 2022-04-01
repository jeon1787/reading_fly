package com.kh.reading_fly.domain.review.svc;

import com.kh.reading_fly.domain.review.dao.ReviewDAO;
import com.kh.reading_fly.domain.review.dto.ReviewDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReviewSVCImpl implements ReviewSVC{

  private final ReviewDAO reviewDAO;


  @Override
  public List<ReviewDTO> reSelectAll(Long risbn) {
    return reviewDAO.reSelectAll(risbn);
  }

  @Override
  public ReviewDTO reSelectOne(Long rnum) {
    return reviewDAO.reSelectOne(rnum);
  }

  @Override
  public ReviewDTO reCreate(ReviewDTO reviewDTO) {
    return reviewDAO.reCreate(reviewDTO);
  }

  @Override
  public ReviewDTO reUpdate(ReviewDTO reviewDTO) {
    return reviewDAO.reUpdate(reviewDTO);
  }

  @Override
  public int reDelete(Long rnum, String rid) {
    return reviewDAO.reDelete(rnum, rid);
  }
}
