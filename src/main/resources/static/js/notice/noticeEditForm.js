'use strict';

  const $modifyBtn = document.getElementById('modifyBtn');
  const $cancelBtn = document.getElementById('cancelBtn');
  const $nEditTitle = document.getElementById('nEditTitle');
  const $nEditContent = document.getElementById('nEditContent');

  $modifyBtn.addEventListener('click', e=>{

    if($nEditTitle.value == "") {
      alert('제목을 입력해주세요');
      return;
    }

    if($nEditContent.value == "") {
      alert('내용을 입력해주세요');
      return;
    }

    if($nEditTitle.value.length > 20) {
      alert('글자수 제한을 초과해서 입력 불가');
      return;
    }

    if($nEditContent.value.length > 1000) {
      alert('글자수 제한을 초과해서 입력 불가');
      return;
    }

    if(!confirm('저장하시겠습니까?')) return;

    const $form = e.target.closest('form');
    const nNum = $form.dataset.nNum;
    $form.action = `/notices/${nNum}`;
    $form.submit();
  });

  $cancelBtn.addEventListener('click', e=>{
  if(!confirm('변경사항이 저장되지 않을 수 있습니다')) return;

      const $form = e.target.closest('form');
      const nNum = $form.dataset.nNum;
      const url = `/notices/${nNum}/detail`;
      location.href = url;
    });