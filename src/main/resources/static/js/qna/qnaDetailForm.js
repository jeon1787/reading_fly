'use strict';
//답글
const $replyBtn = document.getElementById('replyBtn');
$replyBtn?.addEventListener('click',e=>{
  const url = `/qna/${qNum.value}/reply`;
  location.href = url;
});
//수정
const $editBtn = document.getElementById('editBtn');
$editBtn?.addEventListener('click',e=>{
  const url = `/qna/${qNum.value}/edit`;
  location.href = url;
});
//삭제
const $delBtn = document.getElementById('delBtn');
$delBtn?.addEventListener('click',e=>{
  if(confirm('삭제하시겠습니까?')){
    const url = `/qna/${qNum.value}/del`;
    location.href = url;
  }
});
//목록
const $listBtn = document.getElementById('listBtn');
$listBtn?.addEventListener('click',e=>{
  location.href="/qna";
});