'use strict';

 const $modifyBtn = document.getElementById('modifyBtn');
    const $cancelBtn = document.getElementById('cancelBtn');

    $modifyBtn.addEventListener('click', e=>{
      const $form = e.target.closest('form');
      const nNum = $form.dataset.nNum;
      $form.action = `/notices/${nNum}`;
      $form.submit();
    });
    $cancelBtn.addEventListener('click', e=>{
      const $form = e.target.closest('form');
      const nNum = $form.dataset.nNum;
      const url = `/notices/${nNum}/detail`;
      location.href = url;
    });