package com.kh.reading_fly.web.controller;



import com.kh.reading_fly.domain.review.dto.ReviewDTO;
import com.kh.reading_fly.domain.review.svc.ReviewSVC;
import com.kh.reading_fly.web.form.member.login.LoginMember;
import com.kh.reading_fly.web.form.review.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/review")
public class ApiReviewController {

  private final ReviewSVC reviewSVC;


  //댓글 전체 조회
  @GetMapping("/{risbn}")
  public ReviewApiResult<Object> list(@PathVariable String risbn) {
    log.info("list() 호출됨!");

    List<ReviewDTO> list = reviewSVC.selectAll(risbn);
    List<ReviewItemForm> items = new ArrayList<>();

    for (ReviewDTO reviewDTO : list) {
      ReviewItemForm item = new ReviewItemForm();
      BeanUtils.copyProperties(reviewDTO, item);

      LocalDate reviewDate = reviewDTO.getRudate().toLocalDate();
      LocalDate today = LocalDate.now();

      if (reviewDate.equals(today)) {
        item.setRudate(reviewDTO.getRudate().toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm")).toString());
      } else {
        item.setRudate(reviewDTO.getRudate().toLocalDate().toString());
      }

      items.add(item);
    }

    return new ReviewApiResult<>("00", "success", items);

  }


  @GetMapping("/{rnum}/detail")
  public ReviewApiResult<Object> detail(@PathVariable Long rnum){
    log.info("detail() 호출됨!");

    ReviewDTO reviewDTO = reviewSVC.selectOne(rnum);

    ReviewDetailForm detailForm = new ReviewDetailForm();
    BeanUtils.copyProperties(reviewDTO, detailForm);

    LocalDate reviewDate = reviewDTO.getRudate().toLocalDate();
    LocalDate today = LocalDate.now();

    if (reviewDate.equals(today)) {
      detailForm.setRudate(reviewDTO.getRudate().toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm")).toString());
    } else {
      detailForm.setRudate(reviewDTO.getRudate().toLocalDate().toString());
    }

    return new ReviewApiResult<>("00", "success", detailForm);
  }

  //댓글 등록
  @PostMapping("/{risbn}")
  public ReviewApiResult<Object> add(@PathVariable String risbn,
                                     @RequestBody ReviewAddForm reviewAddForm,
                                     HttpSession session){
    log.info("add() 호출됨!");
    log.info("reviewAddForm={}",reviewAddForm);

    ReviewDTO reviewDTO = new ReviewDTO();
    reviewDTO.setRcontent(reviewAddForm.getRcontent());
    reviewDTO.setRisbn(risbn);
    LoginMember loginMember = (LoginMember) session.getAttribute("loginMember");//세션에서 로그인 정보 가져오기
    reviewDTO.setRid(loginMember.getId());

    ReviewDTO savedReview = reviewSVC.create(reviewDTO);

    return new ReviewApiResult<>("00", "success", savedReview);
  }

  //댓글 수정
  @PatchMapping("/{risbn}")
  public ReviewApiResult<Object> edit(@PathVariable String risbn,
                                @RequestBody ReviewEditForm revirwEditForm,
                                HttpSession session){
    log.info("edit() 호출됨!");

    LoginMember loginMember = (LoginMember) session.getAttribute("loginMember");
    if(!loginMember.getId().equals(revirwEditForm.getRid())){
      log.info("edit() : 아이디 불일치");
      return null;
    }

    ReviewDTO reviewDTO = new ReviewDTO();
    reviewDTO.setRnum(revirwEditForm.getRnum());
    reviewDTO.setRid(revirwEditForm.getRid());
    reviewDTO.setRcontent(revirwEditForm.getRcontent());

    ReviewDTO modifiedReview = reviewSVC.update(reviewDTO);

    return new ReviewApiResult<>("00", "success", modifiedReview);
  }

  //댓글 삭제
  @DeleteMapping("/{rnum}")
  public ReviewApiResult<Object> delete(@PathVariable Long rnum,
                                  HttpSession session){
    log.info("delete() 호출됨!");

    LoginMember loginMember = (LoginMember)session.getAttribute("loginMember");
    int result = reviewSVC.delete(rnum, loginMember.getId());
    ReviewApiResult<Object> reviewApiResult = null;

    if(result == 1){
      reviewApiResult = new ReviewApiResult<>("00", "success", "댓글 삭제를 성공했습니다.");
      log.info("apiResult = {}", reviewApiResult);
    }else{
      reviewApiResult = new ReviewApiResult<>("01", "fail", "댓글 삭제를 실패했습니다.");
      log.info("apiResult = {}", reviewApiResult);
    }

    return reviewApiResult;
  }








}
