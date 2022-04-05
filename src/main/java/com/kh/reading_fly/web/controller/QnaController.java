package com.kh.reading_fly.web.controller;

import com.kh.reading_fly.domain.common.paging.FindCriteria;
import com.kh.reading_fly.domain.common.paging.PageCriteria;
import com.kh.reading_fly.domain.common.uploadFile.dto.UploadFileDTO;
import com.kh.reading_fly.domain.common.uploadFile.svc.UploadFileSVC;
import com.kh.reading_fly.domain.qna.dao.QnaFilterCondition;
import com.kh.reading_fly.domain.qna.dto.QnaDTO;
import com.kh.reading_fly.domain.qna.svc.QnaSVC;
import com.kh.reading_fly.web.form.member.login.LoginMember;
import com.kh.reading_fly.web.form.qna.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Controller
@RequestMapping("/qna")
@RequiredArgsConstructor
public class QnaController {
  private final QnaSVC qnaSVC;
  private final UploadFileSVC uploadFileSVC;

  @Autowired
  @Qualifier("fc10") //동일한 타입의 객체가 여러개있을때 빈이름을 명시적으로 지정해서 주입받을때
  private FindCriteria fc;

  //작성양식
  @GetMapping("/add")
//  public String addForm(Model model) {
//    model.addAttribute("addForm", new AddForm());
//    return "bbs/addForm";
//  }
  public String addForm(
      Model model,
      HttpSession session) {

    LoginMember loginMember = (LoginMember) session.getAttribute("loginMember");//세션에서 로그인 정보 가져오기
//    LoginMember loginMember = (LoginMember)session.getAttribute(SessionConst.LOGIN_MEMBER);

    QnaAddForm qnaAddForm = new QnaAddForm();
//    qnaAddForm.setQEmail(loginMember.getEmail());
    qnaAddForm.setQNickname(loginMember.getNickname());
    model.addAttribute("qnaAddForm", qnaAddForm);

    return "qna/qnaAddForm";
  }

  //작성처리
  @PostMapping("/add")
  public String add(
      @Valid @ModelAttribute QnaAddForm qnaAddForm,
      BindingResult bindingResult,
      HttpSession session,
      RedirectAttributes redirectAttributes) {

    if(bindingResult.hasErrors()) {
      return "qna/qnaAddForm";
    }
    QnaDTO qna = new QnaDTO();
    BeanUtils.copyProperties(qnaAddForm, qna);

    //세션 가져오기
    LoginMember loginMember = (LoginMember) session.getAttribute("loginMember");//세션에서 로그인 정보 가져오기
//    LoginMember loginMember = (LoginMember)session.getAttribute(SessionConst.LOGIN_MEMBER);
    //세션 정보가 없으면 로그인페이지로 이동
//    if(loginMember == null){
//      return "redirect:/login";
//    }
    String nickname = loginMember.getNickname();
    log.info("nickname={}", nickname);

    //세션에서 이메일,별칭가져오기
//    qna.setQEmail(loginMember.getEmail());
    qna.setQNickname(loginMember.getNickname());

    Long originId = 0l;
    //파일첨부유무
    if(qnaAddForm.getFiles().size() == 0) {
      originId = qnaSVC.saveOrigin(qna);
    }else{
      originId = qnaSVC.saveOrigin(qna, qnaAddForm.getFiles());
    }

    redirectAttributes.addAttribute("id", originId);
    // <=서버응답 302 get http://서버:port/bbs/10
    // =>클라이언트요청 get http://서버:port/bbs/10

    return "redirect:/qna/{id}";
  }

  //목록
  @GetMapping({"/list",
      "/list/{reqPage}",
      "/list/{reqPage}/{searchType}/{keyword}"})
  public String listAndReqPage(
      @PathVariable(required = false) Optional<Integer> reqPage,
      @PathVariable(required = false) Optional<String> searchType,
      @PathVariable(required = false) Optional<String> keyword,
      Model model) {

    log.info("/list 요청됨");
    //요청없으면 1
    Integer page = reqPage.orElse(1);

    log.info("/list 요청됨{},{},{},{}",reqPage,searchType,keyword);

    //FindCriteria 값 설정
    fc.getRc().setReqPage(reqPage.orElse(1)); //요청페이지, 요청없으면 1
    fc.setSearchType(searchType.orElse(""));  //검색유형
    fc.setKeyword(keyword.orElse(""));        //검색어

    List<QnaDTO> list = null;
    //검색어 있음
    if(searchType.isPresent() && keyword.isPresent()){
      QnaFilterCondition filterCondition = new QnaFilterCondition(
          fc.getRc().getStartRec(), fc.getRc().getEndRec(),
          searchType.get(),
          keyword.get());
      fc.setTotalRec(qnaSVC.totalCount(filterCondition));
      fc.setSearchType(searchType.get());
      fc.setKeyword(keyword.get());
      list = qnaSVC.findAll(filterCondition);

      //검색어 없음
    }else {
      //총레코드수
      fc.setTotalRec(qnaSVC.totalCount());
      list = qnaSVC.findAll(fc.getRc().getStartRec(), fc.getRc().getEndRec());
    }

    List<QnaListForm> partOfList = new ArrayList<>();
    for (QnaDTO qna : list) {
      QnaListForm qnaListForm = new QnaListForm();
      BeanUtils.copyProperties(qna, qnaListForm);
      partOfList.add(qnaListForm);
    }

    model.addAttribute("list", partOfList);
    model.addAttribute("fc",fc);

    return "qna/qnaList";
  }

  //조회
  @GetMapping("/{id}")
  public String detail(
      @PathVariable Long id,
      Model model) {

    QnaDTO detailQna = qnaSVC.findByQNum(id);

    QnaDetailForm qnaDetailForm = new QnaDetailForm();

    BeanUtils.copyProperties(detailQna, qnaDetailForm);
    model.addAttribute("qnaDetailForm", qnaDetailForm);

    //2) 첨부파일 조회
    List<UploadFileDTO> attachFiles = uploadFileSVC.findFilesByCodeWithRnum("Q", detailQna.getQNum());
    if(attachFiles.size() > 0){
      log.info("attachFiles={}",attachFiles);
      model.addAttribute("attachFiles", attachFiles);
    }

    model.addAttribute("fc",fc);

    return "qna/qnaDetailForm";
  }
  //삭제
  @GetMapping("/{id}/del")
  public String del(@PathVariable long id) {

    qnaSVC.deleteByQNum(id);

    return "redirect:/qna/list";
  }
  //수정양식
  @GetMapping("/{id}/edit")
  public String editForm(@PathVariable Long id, Model model) {

    QnaDTO qna = qnaSVC.findByQNum(id);

    QnaEditForm qnaEditForm = new QnaEditForm();
    BeanUtils.copyProperties(qna,qnaEditForm);
    model.addAttribute("qnaEditForm",qnaEditForm);

    //2) 첨부파일 조회
    List<UploadFileDTO> attachFiles = uploadFileSVC.findFilesByCodeWithRnum("Q", qnaEditForm.getQNum());
    if(attachFiles.size() > 0){
      log.info("attachFiles={}",attachFiles);
      model.addAttribute("attachFiles", attachFiles);
    }

    return "qna/qnaEditForm";
  }

  //수정처리
  @PostMapping("/{id}/edit")
  public String edit(@PathVariable Long id,
                     @Valid @ModelAttribute QnaEditForm qnaEditForm,
                     BindingResult bindingResult,
                     RedirectAttributes redirectAttributes) {

    if(bindingResult.hasErrors()) {
      return "qna/qnaEditForm";
    }

    QnaDTO qna = new QnaDTO();
    BeanUtils.copyProperties(qnaEditForm, qna);
    qnaSVC.updateByQNum(id, qna);

    if(qnaEditForm.getFiles().size() == 0) {
      qnaSVC.updateByQNum(id, qna);
    }else{
      qnaSVC.updateByBbsId(id, qna, qnaEditForm.getFiles());
    }


    redirectAttributes.addAttribute("id",id);

    return "redirect:/qna/{id}";
  }

  //답글작성양식
  @GetMapping("/{id}/reply")
  public String replyForm(@PathVariable Long id,
                          Model model, HttpSession session) {

    QnaDTO parentQna = qnaSVC.findByQNum(id);
    QnaReplyForm qnaReplyForm = new QnaReplyForm();
    qnaReplyForm.setQTitle("ㄴ답변:"+parentQna.getQTitle());

    //세션에서 로그인정보 가져오기
    LoginMember loginMember = (LoginMember) session.getAttribute("loginMember");//세션에서 로그인 정보 가져오기
//    LoginMember loginMember = (LoginMember)session.getAttribute(SessionConst.LOGIN_MEMBER);
//    qnaReplyForm.setQEmail(loginMember.getEmail());
    qnaReplyForm.setQNickname(loginMember.getNickname());

    model.addAttribute("qnaReplyForm", qnaReplyForm);
    return "qna/qnaReplyForm";
  }

  //답글작성처리
  @PostMapping("/{id}/reply")
  public String reply(
      @PathVariable Long id,  //부모글의 qNum
      @Valid QnaReplyForm qnaReplyForm,
      BindingResult bindingResult,
      RedirectAttributes redirectAttributes
  ) {

    if(bindingResult.hasErrors()) {
      return "qna/qnaReplyForm";
    }

    QnaDTO replyQna = new QnaDTO();
    BeanUtils.copyProperties(qnaReplyForm,replyQna);

    //부모글의 ,qNum,qgroup,qstep,qindent 참조
    appendInfoOfParentQna(id, replyQna);

    //답글저장(return 답글번호)
    Long replyQNum = qnaSVC.saveReply(id, replyQna);

    redirectAttributes.addAttribute("id",replyQNum);

    return "redirect:/qna/{id}";
  }

  //부모글의 QNum,qgroup,qstep,qindent
  private void appendInfoOfParentQna(Long parentQNum, QnaDTO replyQna) {
    QnaDTO parentQna = qnaSVC.findByQNum(parentQNum);
    replyQna.setPQNum(parentQna.getPQNum());
    replyQna.setQGroup(parentQna.getQGroup());
    replyQna.setQStep(parentQna.getQStep());
    replyQna.setQIndent(parentQna.getQIndent());
  }

}
