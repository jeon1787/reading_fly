package com.kh.reading_fly.web.controller;

import com.kh.reading_fly.domain.comment.dto.CommentDTO;
import com.kh.reading_fly.domain.comment.svc.CommentSVC;
import com.kh.reading_fly.web.form.comment.AddForm;
import com.kh.reading_fly.web.form.comment.ApiResult;
import com.kh.reading_fly.web.form.comment.ItemForm;
import com.kh.reading_fly.web.form.login.LoginMember;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

  //댓글 조회
  @GetMapping("/{cbnum}")
  public ApiResult<Object> list(@PathVariable Long cbnum){
    log.info("list() 호출됨!");

    List<CommentDTO> list =commentSVC.findAll(cbnum);
    List<ItemForm> items = new ArrayList<>();

    for(CommentDTO commentDTO : list){
      ItemForm item = new ItemForm();
//      item.setCnum(commentDTO.getCnum());
//      item.setNickname(commentDTO.getNickname());
//      item.setCcontent(commentDTO.getCcontent());
      BeanUtils.copyProperties(commentDTO, item);

      LocalDate boardDate = commentDTO.getCudate().toLocalDate();
//      log.info("boardDate={}", boardDate);
      LocalDate today = LocalDate.now();
//      log.info("today={}", today);

      if(boardDate.equals(today)){//오늘 작성된 글이면
        item.setCudate(commentDTO.getCudate().toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm")).toString());
      }else{//오늘 이전에 작성된 글이면
        item.setCudate(commentDTO.getCudate().toLocalDate().toString());
      }

      items.add(item);
    }

    return new ApiResult<>("00", "success", items);
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
  }
}
