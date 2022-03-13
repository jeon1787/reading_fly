'use strict';

  const $writeBtn = document.getElementById('writeBtn');
  const $listBtn = document.getElementById('listBtn');
  const $nTitle = document.getElementById('nTitle');
  const $nContent = document.getElementById('nContent');
  $writeBtn.addEventListener('click', e=>{

    if($nTitle.value == "") {
      alert('제목을 입력해주세요');
      return;
    }

    if($nContent.value == "") {
      alert('내용을 입력해주세요');
      return;
    }

    if($nTitle.value.length > 20) {
      alert('글자수 제한을 초과해서 입력 불가');
      return;
    }

    if($nContent.value.length > 1000) {
      alert('글자수 제한을 초과해서 입력 불가');
      return;
    }

    e.target.closest('form').submit();
   });

  $listBtn.addEventListener('click', ()=>{
    const url = `/notices/all`;
    location.href = url;
  });

 $cancelBtn.addEventListener('click', e=>{
  if(!confirm('변경사항이 저장되지 않을 수 있습니다')) return;
    const url = `/notices/all`;
    location.href = url;
  });