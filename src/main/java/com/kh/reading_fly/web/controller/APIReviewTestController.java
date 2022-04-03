package com.kh.reading_fly.web.controller;


import com.kh.reading_fly.domain.revirwtest.dto.ReviewReqDTO;
import com.kh.reading_fly.domain.revirwtest.dto.ReviewTestDTO;
import com.kh.reading_fly.domain.revirwtest.svc.ReviewTestSVC;
import com.kh.reading_fly.web.form.member.login.LoginMember;
import com.kh.reading_fly.web.form.reviewTest.ReviewTestAPIResult;
import com.kh.reading_fly.web.form.reviewTest.ReviewTestForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@Transactional
@RequestMapping("/review")
public class APIReviewTestController {

  private final ReviewTestSVC reviewTestSVC;

  //리뷰등록
  @PostMapping("/")
  public ReviewTestAPIResult addReview(
      @ModelAttribute ReviewTestForm reviewTestForm,
      HttpServletRequest request) throws IllegalStateException, IOException {

    ReviewTestAPIResult reviewTestAPIResult;

    HttpSession session = request.getSession(false);
    if(session == null || session.getAttribute("loginMember") == null) {
      reviewTestAPIResult = new ReviewTestAPIResult("01","로그인이 만료되었어요 다시 로그인해주세요.",null);
      return reviewTestAPIResult;
    }

    //리뷰작성폼→리뷰DTO
    ReviewTestDTO reviewTestDTO = new ReviewTestDTO();
    BeanUtils.copyProperties(reviewTestForm,reviewTestDTO);

    List<ReviewReqDTO> list = reviewTestSVC.registReview(reviewTestDTO);


    reviewTestAPIResult = new ReviewTestAPIResult("00","성공",list);
    return reviewTestAPIResult;
  }



  // //리뷰1개 호출(리뷰수정폼)
  @GetMapping("/")
  public ReviewTestAPIResult findReview(@RequestParam int rnum,
                           HttpServletRequest request) {
    ReviewTestAPIResult reviewTestAPIResult;

    HttpSession session = request.getSession(false);
    if(session == null || session.getAttribute("loginMember") == null) {
      reviewTestAPIResult = new ReviewTestAPIResult("01","로그인이 만료되었어요 다시 로그인해주세요.",null);
      return reviewTestAPIResult;
    }

    LoginMember loginMember = (LoginMember)session.getAttribute("loginMember");
    String userId = loginMember.getId();

    ReviewReqDTO reviewReqDTO	= reviewTestSVC.findReview(rnum);
    String rid = reviewReqDTO.getRid();

    if(!rid.equals(userId)){
      reviewTestAPIResult = new ReviewTestAPIResult("02","작성자의 아이디와 일치하지 않습니다.",null);
      return reviewTestAPIResult;
    }

    reviewTestAPIResult = new ReviewTestAPIResult("00","성공",reviewReqDTO);
    return reviewTestAPIResult;
  }

  //리뷰수정
  @PatchMapping("/")
  public ReviewTestAPIResult modiReview(@ModelAttribute ReviewTestForm reviewTestForm,
                           HttpServletRequest request) throws IllegalStateException, IOException{

    log.info("reviewForm:{}",reviewTestForm);

    ReviewTestAPIResult reviewTestAPIResult;

    HttpSession session = request.getSession(false);
    if(session == null || session.getAttribute("loginMember") == null) {
      reviewTestAPIResult = new ReviewTestAPIResult("01","로그인이 만료되었어요 다시 로그인해주세요.",null);
      return reviewTestAPIResult;
    }

    LoginMember loginMember = (LoginMember)session.getAttribute("loginMember");
    String userId = loginMember.getId();

    ReviewReqDTO review	= reviewTestSVC.findReview(reviewTestForm.getRnum());
    String rid = review.getRid();

    if(!rid.equals(userId)){
      reviewTestAPIResult = new ReviewTestAPIResult("02","작성자의 아이디와 일치하지 않습니다.",null);
      return reviewTestAPIResult;
    }

    ReviewTestDTO reviewTestDTO = new ReviewTestDTO();
    BeanUtils.copyProperties(reviewTestForm,reviewTestDTO);


    List<ReviewReqDTO> list = reviewTestSVC.updateReview(reviewTestDTO);
    reviewTestAPIResult = new ReviewTestAPIResult("00","성공",list);
    return reviewTestAPIResult;
  }

  //리뷰삭제
  @DeleteMapping("/")
  public ReviewTestAPIResult deleteReview(@RequestParam int rnum,
                             HttpServletRequest request) {

    ReviewTestAPIResult reviewTestAPIResult;

    HttpSession session = request.getSession(false);
    if(session == null || session.getAttribute("loginMember") == null) {
      reviewTestAPIResult = new ReviewTestAPIResult("01","로그인이 만료되었어요 다시 로그인해주세요.",null);
      return reviewTestAPIResult;
    }

    LoginMember loginMember = (LoginMember)session.getAttribute("loginMember");
    String userId = loginMember.getId();

    ReviewReqDTO review	= reviewTestSVC.findReview(rnum);
    String rid = review.getRid();

    if(!userId.equals(rid)){
      reviewTestAPIResult = new ReviewTestAPIResult("01","해당 리뷰작성자와의 아이디가 일치하지 않습니다.",null);
      return reviewTestAPIResult;
    }

    List<ReviewReqDTO> list = reviewTestSVC.removeReview(Long.valueOf(review.getRisbn()), rnum);
    reviewTestAPIResult = new ReviewTestAPIResult("00","성공",list);
    return reviewTestAPIResult;
  }






}
