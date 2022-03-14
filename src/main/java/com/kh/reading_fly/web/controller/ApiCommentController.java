package com.kh.reading_fly.web.controller;

import com.kh.reading_fly.domain.comment.dto.CommentDTO;
import com.kh.reading_fly.domain.comment.svc.CommentSVC;
import com.kh.reading_fly.web.form.comment.ApiResult;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comment")
public class ApiCommentController {

  private final CommentSVC commentSVC;

  //댓글 전체 조회
  @GetMapping("/{cbnum}")
  public ApiResult<Object> list(@PathVariable Long cbnum){
    List<CommentDTO> list =commentSVC.findAll(cbnum);
    return new ApiResult<>("00", "success", list);
  }
}
