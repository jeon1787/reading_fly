package com.kh.reading_fly.web.controller;

import com.kh.reading_fly.domain.board.dto.BoardDTO;
import com.kh.reading_fly.domain.common.paging.FindCriteria;
import com.kh.reading_fly.domain.common.paging.PageCriteria;
import com.kh.reading_fly.domain.common.uploadFile.dto.UploadFileDTO;
import com.kh.reading_fly.domain.common.uploadFile.svc.UploadFileSVC;
import com.kh.reading_fly.domain.notice.dao.NoticeFilterCondition;
import com.kh.reading_fly.domain.notice.dto.NoticeDTO;
import com.kh.reading_fly.domain.notice.svc.NoticeSVC;
import com.kh.reading_fly.web.form.member.login.LoginMember;
import com.kh.reading_fly.web.form.notice.NoticeAddForm;
import com.kh.reading_fly.web.form.notice.NoticeDetailForm;
import com.kh.reading_fly.web.form.notice.NoticeEditForm;
import com.kh.reading_fly.web.form.notice.NoticeItem;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/notices")
public class NoticeController {

  private final NoticeSVC noticeSVC;
  private final UploadFileSVC uploadFileSVC;

  @Autowired
  @Qualifier("fc10") //동일한 타입의 객체가 여러개있을때 빈이름을 명시적으로 지정해서 주입받을때
  private FindCriteria fc;

  //  등록화면
  @GetMapping("")
  public String addForm(@ModelAttribute NoticeAddForm noticeAddForm) {
    return "notice/noticeAddForm";
  }

  //  등록처리
  @PostMapping("")
  public String add(
      @Valid
      @ModelAttribute NoticeAddForm noticeAddForm,
      BindingResult bindingResult,
      RedirectAttributes redirectAttributes,
      Model model){

    if(bindingResult.hasErrors()){
      log.info("add/bindingResult={}",bindingResult);
      return "notice/noticeAddForm";
    }

    NoticeDTO notice = new NoticeDTO();
    notice.setNTitle(noticeAddForm.getNTitle());
    notice.setNContent(noticeAddForm.getNContent());

    // 파일첨부유무별 게시글 저장
    Long originId = 0L;
    if(noticeAddForm.getFiles().size() == 0) {
      originId = noticeSVC.write(notice);//게시글 저장
    }else{
      originId = noticeSVC.write(notice, noticeAddForm.getFiles());//게시글 저장 - 파일첨부시
    }

    redirectAttributes.addAttribute("id",originId);

    return "redirect:/notices/{id}/detail";  //http://서버:9080/notices/공지사항번호
  }
  //  상세화면
  @GetMapping("/{nNum}/detail")
  public String detailForm(@PathVariable Long nNum, Model model, HttpSession session){
//    LoginMember loginMember = (LoginMember) session.getAttribute("loginMember");//세션에서 로그인 정보 가져오기
//
//    if(loginMember.getId() != null ) {
//      String id = loginMember.getId();
//      session.setAttribute("id",id);
//    }

    NoticeDTO notice = noticeSVC.findByNoticeId(nNum);


    NoticeDetailForm noticeDetailForm = new NoticeDetailForm();
    noticeDetailForm.setNNum(notice.getNNum());
    noticeDetailForm.setNTitle(notice.getNTitle());
    noticeDetailForm.setNContent(notice.getNContent());
    noticeDetailForm.setNHit(notice.getNHit());
    noticeDetailForm.setNCDate(notice.getNCDate().toLocalDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
    noticeDetailForm.setNUDate(notice.getNUDate().toLocalDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
    model.addAttribute("noticeDetailForm",noticeDetailForm);

    //2) 첨부파일 조회
    List<UploadFileDTO> attachFiles = uploadFileSVC.findFilesByCodeWithRnum("N", noticeDetailForm.getNNum());
    if(attachFiles.size() > 0){
      log.info("attachFiles={}",attachFiles);
      model.addAttribute("attachFiles", attachFiles);
    }

    model.addAttribute("fc",fc);

    return "notice/noticeDetailForm";
  }
  //  수정화면
  @GetMapping("/{nNum}")
  public String editForm(@PathVariable Long nNum, Model model){

    NoticeDTO notice = noticeSVC.findByNoticeId(nNum);

    NoticeEditForm noticeEditForm = new NoticeEditForm();
    noticeEditForm.setNNum(notice.getNNum());
    noticeEditForm.setNTitle(notice.getNTitle());
    noticeEditForm.setNContent(notice.getNContent());

    model.addAttribute("noticeEditForm", noticeEditForm);

    // 첨부파일 조회
    List<UploadFileDTO> attachFiles = uploadFileSVC.findFilesByCodeWithRnum("N", noticeEditForm.getNNum());
    if(attachFiles.size() > 0){
      log.info("attachFiles={}",attachFiles);
      model.addAttribute("attachFiles", attachFiles);
    }

    return "notice/noticeEditForm";
  }
  //  수정처리
  @PatchMapping("/{nNum}")
  public String edit(
      @Valid
      @ModelAttribute NoticeEditForm noticeEditForm,
      BindingResult bindingResult,
      @PathVariable Long nNum,
      RedirectAttributes redirectAttributes
  ){

    if(bindingResult.hasErrors()){
      return "notice/noticeEditForm";
    }

    NoticeDTO notice = new NoticeDTO();
    notice.setNNum(nNum);
    notice.setNTitle(noticeEditForm.getNTitle());
    notice.setNContent(noticeEditForm.getNContent());
    NoticeDTO modifiedNotice = noticeSVC.modify(notice);

    //3) 파일첨부유무별 게시글 수정
    NoticeDTO modifiedNoticeDTO;
    if(noticeEditForm.getFiles().size() == 0) {
      modifiedNoticeDTO = noticeSVC.modify(notice);//게시글 수정
    }else{
      modifiedNoticeDTO = noticeSVC.modify(notice, noticeEditForm.getFiles());//게시글 수정 - 파일첨부시
    }

    redirectAttributes.addAttribute("nNum", modifiedNotice.getNNum());

    return "redirect:/notices/{nNum}/detail";
  }
  //  삭제처리
  @DeleteMapping("{nNum}")
  public String del(@PathVariable Long nNum){
    noticeSVC.remove(nNum);
    return "redirect:/notices/all";
  }
  //  전체목록
  @GetMapping({"/all",
      "/all/{reqPage}",
      "/all/{reqPage}/{searchType}/{keyword}"})
  public String list(
      @PathVariable(required = false) Optional<Integer> reqPage,
      @PathVariable(required = false) Optional<String> searchType,
      @PathVariable(required = false) Optional<String> keyword,
      Model model , HttpSession session){

    log.info("/list 요청됨{},{},{},{}",reqPage,searchType,keyword);

    //FindCriteria 값 설정
    fc.getRc().setReqPage(reqPage.orElse(1)); //요청페이지, 요청없으면 1
    fc.setSearchType(searchType.orElse(""));  //검색유형
    fc.setKeyword(keyword.orElse(""));        //검색어

    List<NoticeDTO> list = null;
    //검색어 있음
    if(searchType.isPresent() && keyword.isPresent()){
      NoticeFilterCondition filterCondition = new NoticeFilterCondition(
          fc.getRc().getStartRec(), fc.getRc().getEndRec(),
          searchType.get(),
          keyword.get());
      fc.setTotalRec(noticeSVC.totalCount(filterCondition));
      fc.setSearchType(searchType.get());
      fc.setKeyword(keyword.get());
      list = noticeSVC.findAll(filterCondition);

      //검색어 없음
    }else {
      //총레코드수
      fc.setTotalRec(noticeSVC.totalCount());
      list = noticeSVC.findAll(fc.getRc().getStartRec(), fc.getRc().getEndRec());
    }

    List<NoticeItem> notices = new ArrayList<>();
    for (NoticeDTO notice : list) {
      NoticeItem item = new NoticeItem();
      item.setNNum(notice.getNNum());
      item.setNTitle(notice.getNTitle());
//      item.setNCDate(notice.getNCDate().toLocalDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
      item.setNUDate(notice.getNUDate().toLocalDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
      item.setNHit(notice.getNHit());
      notices.add(item);
    }

    model.addAttribute("notices", notices);
    model.addAttribute("fc",fc);

    return "notice/noticeList";
  }
}