'use strict';

    const $saveBtn = document.getElementById('saveBtn');
    const $cancelBtn = document.getElementById('cancelBtn');

    $saveBtn.addEventListener('click', e=>{
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