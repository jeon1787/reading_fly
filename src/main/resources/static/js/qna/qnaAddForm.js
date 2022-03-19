'use strict';
//등록
const $writeBtn = document.getElementById('writeBtn');
$writeBtn?.addEventListener("click", e=>{
  writeForm.submit();
});
//목록
const $listBtn = document.getElementById('listBtn');
$listBtn?.addEventListener("click",e=>{
  location.href = "/qna";
});
