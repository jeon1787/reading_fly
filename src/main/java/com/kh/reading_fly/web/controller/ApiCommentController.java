package com.kh.reading_fly.web.controller;

import com.kh.reading_fly.domain.comment.svc.CommentSVC;
import com.kh.reading_fly.web.form.comment.ApiResult;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comments")
public class ApiCommentController {

  private final CommentSVC commentSVC;

  @GetMapping
  public ApiResult<Object> list(){
    return null;
  }
}
