'use strict';
//상세
const $cancelBtn = document.getElementById('cancelBtn');
const $editForm = document.getElementById('editForm');
$cancelBtn?.addEventListener('click',e=>{
  const url = `/qna/${qNum.value}`;
  location.href = url;
});
//목록
const $listBtn = document.getElementById('listBtn');
$listBtn?.addEventListener('click',e=>{
  location.href="/qna/list";
});