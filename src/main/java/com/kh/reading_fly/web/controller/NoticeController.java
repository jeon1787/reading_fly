package com.kh.reading_fly.web.controller;

import com.kh.reading_fly.domain.common.paging.PageCriteria;
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

  @Autowired
  @Qualifier("pc10") //동일한 타입의 객체가 여러개있을때 빈이름을 명시적으로 지정해서 주입받을때
  private PageCriteria pc;

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

    Long writedNotice = noticeSVC.write(notice);
    redirectAttributes.addAttribute("nNum",writedNotice);

    return "redirect:/notices/{nNum}/detail";  //http://서버:9080/notices/공지사항번호
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
  @GetMapping({"/all","/all/{reqPage}"})
  public String list(
      @PathVariable(required = false) Optional<Integer> reqPage,
      Model model , HttpSession session){

    log.info("/list 요청됨");
    //요청없으면 1
    Integer page = reqPage.orElse(1);

    //요청페이지
    pc.getRc().setReqPage(page);

    //총레코드수
    pc.setTotalRec(noticeSVC.totalCount());
    Long a = pc.getTotalRec();
    log.info("total={}", a);
    log.info("getStartRec={}", pc.getRc().getStartRec());
    log.info("getEndRec={}", pc.getRc().getEndRec());
    List<NoticeDTO> list = noticeSVC.findAll(pc.getRc().getStartRec(), pc.getRc().getEndRec());

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
    model.addAttribute("pc",pc);

    return "notice/noticeList";
  }
}