'use strict';

 const $writeBtn = document.getElementById('writeBtn');
    const $listBtn = document.getElementById('listBtn');
    $writeBtn.addEventListener('click', e=>{
      e.target.closest('form').submit();
    });
    $listBtn.addEventListener('click', ()=>{
      const url = `/notices/all`;
      location.href = url;
    });