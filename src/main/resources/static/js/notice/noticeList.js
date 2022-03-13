'use strict';

  const $writeBtn = document.getElementById('writeBtn');
    $writeBtn.addEventListener('click',()=>{
      const url = `/notices`;
      location.href = url
    });