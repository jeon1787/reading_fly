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
      }).then(res=>res.json())
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

    if(e.target.textContent == '수정'){
      console.log("댓글 수정 클릭");

      //로그인 체크
      const chk = document.querySelector('.form').dataset.loginChk;
      console.log();

      //비로그인 상태
//      if(chk == 'true'){
//        console.log(chk);
//        console.log("로그인이 필요한 서비스입니다.");
//        alert("로그인이 필요한 서비스입니다.");
//        return;
//      //로그인 상태
//      }else{
//
//        const $comment = e.target.closest('.comment');
//
//        const nickname = $comment.children[0].children[0].textContent;
//        const content = $comment.children[1].textContent;
//        const date = $comment.children[2].children[0].textContent;
//
//        $comment.innerHTML =
//        `<div class="comment-head">
//            <span>${nickname}</span>
//        </div>
//        <textarea id="reply" name="" cols="104" rows="10" value="${content}"></textarea>
//        <div><span>${date}</span><button>저장</button><button>취소</button></div>`;
//        alert("else");
//        return;
//
//      };
    }

  })

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