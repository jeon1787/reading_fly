'use strict';
  //dom 이벤트
  document.addEventListener('DOMContentLoaded', e=>{
    console.log('DOM 생성');

    const bnum = document.querySelector('.form').dataset.bnum;
    list_f(bnum);
  });

  //댓글목록 조회 함수
  function list_f(bnum){
    console.log("list_f 호출됨!");
    const chk = document.querySelector('.form').dataset.loginChk;
    console.log("확인용"+chk);



    fetch(`http://localhost:9080/api/comment/${bnum}`,{method:'GET'})
    .then(res => res.json())
    .then(res => {
    console.log(res);
    displayComments_f(res.data);
    })
    .catch(err => console.error('Err:',err))
  }

  //댓글목록 출력 함수
  function displayComments_f(comments){
    console.log("displayComments_f 호출됨!");

    let content = ``;
    comments.forEach(comment => {
      content +=
       `<div class="comment">
          <div class="comment-head">
            <input class="cnum" type="hidden" value="${comment.cnum}">
            <input class="cid" type="hidden" value="${comment.cid}">
            <span>${comment.nickname}</span>
            <span><a class="editBtn" href="">수정</a><a class="delBtn" href="">삭제</a></span>
          </div>
          <div class="comment-content" style="white-space:pre-wrap;">${comment.ccontent}</div>
          <div><span>${comment.cudate}</span><a href="">대댓글쓰기</a></div>
        </div>`;
    });

    const $commentList = document.querySelector('.comment-list');
    $commentList.innerHTML = content;
  }

  //댓글등록 함수
  addBtn.addEventListener('click', e=>{
    const chk = document.querySelector('.form').dataset.loginChk;
    console.log(chk);

    //비로그인 상태
    if(chk == "false"){
      console.log(chk);
      const bnum = document.querySelector('.form').dataset.bnum;
      location.href = `/login?redirectUrl=/board/${bnum}/detail`;
      return;
    //로그인 상태
    }else{
      const bnum = document.querySelector('.form').dataset.bnum;

      const addForm = {};
      addForm.ccontent = reply.value;


      fetch(`http://localhost:9080/api/comment/${bnum}`,{
        method:'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(addForm)  // js객체를 => json 포맷 문자열 변환
      })
      .then(res=>res.json())
      .then(res=>{
        console.log(res);
        list_f(bnum);
      })
      .catch(err => console.error('Err:',err));
    }
  })

  //댓글수정 버튼 클릭시
  const $commentArea = document.querySelector('.comment-area');
  $commentArea.addEventListener('click',e=>{
    //a태그 이벤트 막기
    e.preventDefault();

    //수정 버튼 체크
    if(e.target.textContent == '수정'){
      console.log("댓글 수정 클릭");
      const $comment = e.target.closest('.comment');

      //로그인 체크
      const id = document.querySelector('.form').dataset.id;
      console.log("로그인한 id : "+id);

      const cid = $comment.children[0].children[1].value;
      console.log("댓글작성자 cid : "+cid);

      //로그인 상태
      if(id == cid){
        //기존 수정모드 해제
        modifyModeCancel();

        //수정모드 변경
        modifyMode($comment, cid);
      //비로그인 상태
      }else{
        console.log("로그인이 필요한 서비스입니다.");
        alert("로그인이 필요한 서비스입니다.");
        return;
      };
    }

  })

  //기존 수정모드 해제
  function modifyModeCancel(){
    if(document.getElementById("saveBtn") != null){
      console.log("modifyModeCancel() 실행");
      const $saveBtn = document.getElementById("saveBtn");
      const $closeComment = $saveBtn.closest('.comment');
      console.log($closeComment);
      const cnum = $closeComment.children[0].children[0].value;
      fetch(`http://localhost:9080/api/comment/${cnum}/detail`,{method:'GET'})
      .then(res=>res.json())
      .then(res=>{
        console.log(res);
        displayComment_f(res.data, $closeComment);
      })
      .catch(err => console.error('Err:',err));
    return;
    }
  }

  //단건 조회
  function displayComment_f(detailForm, $closeComment){
    $closeComment.innerHTML =
       `<div class="comment">
          <div class="comment-head">
            <input class="cnum" type="hidden" value="${detailForm.cnum}">
            <input class="cid" type="hidden" value="${detailForm.cid}">
            <span>${detailForm.nickname}</span>
            <span><a class="editBtn" href="">수정</a><a class="delBtn" href="">삭제</a></span>
          </div>
          <div class="comment-content" style="white-space:pre-wrap;">${detailForm.ccontent}</div>
          <div><span>${detailForm.cudate}</span><a href="">대댓글쓰기</a></div>
        </div>`;
    return;
  }

  //수정모드 변경
  function modifyMode($comment, cid){
    console.log("modifyMode() 실행");
    const cnum = $comment.children[0].children[0].value;
    const nickname = $comment.children[0].children[2].textContent;
    const content = $comment.children[1].textContent;
    const cudate = $comment.children[2].children[0].textContent;
    console.log(cnum);
    console.log(nickname);
    console.log(content);
    console.log(cudate);

    $comment.innerHTML =
    `<div class="comment">
       <div class="comment-head">
         <input class="cnum" type="hidden" value="${cnum}">
         <input class="cid" type="hidden" value="${cid}">
         <span>${nickname}</span>
         <span>${cudate}</span>
       </div>
       <textarea class="comment-content">${content}</textarea>
       <div><button id="saveBtn">저장</button><button id="cancelBtn">취소</button></div>       </div>`;
    //alert("else");
    return;
  }//end of modifyMode

  //댓글삭제 버튼 클릭시

  //게시글 수정 버튼 클릭시
  const $editBtn = document.getElementById('editBtn');
  $editBtn?.addEventListener('click', e=>{
    const bnum = e.target.dataset.bnum;
    const url = `/board/${bnum}/edit`;
    location.href = url;
  })

  //게시글 삭제 버튼 클릭시
  const $delBtn = document.getElementById('delBtn');
  $delBtn?.addEventListener('click', e=>{
    //댓글이 달린경우 삭제가 안된다 알려야함
    if(window.confirm('댓글이 있는 게시글은 본문만 삭제됩니다.\n정말 삭제하시겠습니까?')){
      const bnum = e.target.dataset.bnum;
      const url = `/board/${bnum}/del`;
      location.href = url;
    }
  })