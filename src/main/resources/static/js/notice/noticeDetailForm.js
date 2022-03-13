'use strict';

  const $editBtn  = document.getElementById('editBtn');
  const $delBtn   = document.getElementById('delBtn');
  const $listBtn  = document.getElementById('listBtn');

  $editBtn.addEventListener('click',e=>{
    const noticeId = e.target.closest('form').dataset.nNum;
    const url = `/notices/${noticeId}`;
    location.href = url;
  });
  $delBtn.addEventListener('click', e => {
    if(!confirm('삭제하시겠습니까?')) return;

      const $form =  e.target.closest('form');
      const nNum = $form.dataset.nNum;
      $form.action = `/notices/${nNum}`;
      $form.submit();
  });

  $listBtn.addEventListener('click',()=>{
    const url = `/notices/all`;
    location.href = url;
  });