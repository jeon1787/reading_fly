package com.kh.reading_fly.web.controller;

import com.kh.reading_fly.domain.comment.dto.CommentDTO;
import com.kh.reading_fly.domain.comment.svc.CommentSVC;
import com.kh.reading_fly.web.form.comment.*;
import com.kh.reading_fly.web.form.member.login.LoginMember;
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
@RequestMapping("/api/comment")
public class ApiCommentController {

  private final CommentSVC commentSVC;

  //댓글 전체 조회
  @GetMapping("/{cbnum}")
  public ApiResult<Object> list(@PathVariable Long cbnum) {
    log.info("list() 호출됨!");

    List<CommentDTO> list = commentSVC.findAll(cbnum);
    List<ItemForm> items = new ArrayList<>();

    for (CommentDTO commentDTO : list) {
      ItemForm item = new ItemForm();
//      item.setCnum(commentDTO.getCnum());
//      item.setCid(commentDTO.getCid());
//      item.setNickname(commentDTO.getNickname());
//      item.setCcontent(commentDTO.getCcontent());
      BeanUtils.copyProperties(commentDTO, item);

      LocalDate commentDate = commentDTO.getCudate().toLocalDate();
//      log.info("boardDate={}", boardDate);
      LocalDate today = LocalDate.now();
//      log.info("today={}", today);

      if (commentDate.equals(today)) {//오늘 작성된 글이면
        item.setCudate(commentDTO.getCudate().toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm")).toString());
      } else {//오늘 이전에 작성된 글이면
        item.setCudate(commentDTO.getCudate().toLocalDate().toString());
      }

      items.add(item);
    }

    return new ApiResult<>("00", "success", items);
  }//end of list

  //댓글 단건 조회
  @GetMapping("/{cnum}/detail")
  public ApiResult<Object> detail(@PathVariable Long cnum){
    log.info("detail() 호출됨!");
    
    CommentDTO commentDTO = commentSVC.findByCnum(cnum);
    DetailForm detailForm = new DetailForm();

    BeanUtils.copyProperties(commentDTO, detailForm);

    LocalDate commentDate = commentDTO.getCudate().toLocalDate();
//      log.info("boardDate={}", boardDate);
    LocalDate today = LocalDate.now();
//      log.info("today={}", today);

    if (commentDate.equals(today)) {//오늘 작성된 글이면
      detailForm.setCudate(commentDTO.getCudate().toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm")).toString());
    } else {//오늘 이전에 작성된 글이면
      detailForm.setCudate(commentDTO.getCudate().toLocalDate().toString());
    }

    return new ApiResult<>("00", "success", detailForm);
  }

  //댓글 등록
  @PostMapping("/{cbnum}")
  public ApiResult<Object> add(@PathVariable Long cbnum,
                               @RequestBody AddForm addForm,
                               HttpSession session){
    log.info("add() 호출됨!");

    CommentDTO commentDTO = new CommentDTO();
    commentDTO.setCcontent(addForm.getCcontent());
    commentDTO.setCbnum(cbnum);
    LoginMember loginMember = (LoginMember) session.getAttribute("loginMember");//세션에서 로그인 정보 가져오기
    commentDTO.setCid(loginMember.getId());

    CommentDTO savedComment = commentSVC.write(commentDTO);

    return new ApiResult<>("00", "success", savedComment);
  }//end of add

  //댓글 수정
  @PatchMapping("/{cbnum}")
  public ApiResult<Object> edit(@PathVariable Long cbnum,
                               @RequestBody EditForm editForm,
                               HttpSession session){
    log.info("edit() 호출됨!");

    LoginMember loginMember = (LoginMember) session.getAttribute("loginMember");
    if(!loginMember.getId().equals(editForm.getCid())){
      log.info("edit() : 아이디 불일치");
      return null;
    }

    CommentDTO commentDTO = new CommentDTO();
    commentDTO.setCnum(editForm.getCnum());
    commentDTO.setCid(editForm.getCid());
    commentDTO.setCcontent(editForm.getCcontent());

    CommentDTO modifiedComment = commentSVC.modify(commentDTO);

    return new ApiResult<>("00", "success", modifiedComment);
  }//end of edit

  //댓글 삭제
  @DeleteMapping("/{cnum}")
  public ApiResult<Object> delete(@PathVariable Long cnum,
                                  HttpSession session){
    log.info("delete() 호출됨!");

    LoginMember loginMember = (LoginMember)session.getAttribute("loginMember");
    int result = commentSVC.remove1(cnum, loginMember.getId());
    ApiResult<Object> apiResult = null;

    if(result == 1){
      apiResult = new ApiResult<>("00", "success", "댓글 삭제를 성공했습니다.");
      log.info("apiResult = {}", apiResult);
    }else{
      apiResult = new ApiResult<>("01", "fail", "댓글 삭제를 실패했습니다.");
      log.info("apiResult = {}", apiResult);
    }

    return apiResult;
  }
}
