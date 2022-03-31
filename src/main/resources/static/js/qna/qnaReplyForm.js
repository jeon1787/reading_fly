'use strict';
//등록
writeBtn?.addEventListener("click", e=>{
  writeForm.submit();
});
//목록
listBtn?.addEventListener("click",e=>{
  location.href = "/qna/list";
});
