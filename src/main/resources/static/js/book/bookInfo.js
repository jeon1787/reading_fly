'use strict';
const $infoForm = document.querySelector('.info-form');
const $saveBtn = document.querySelector('.saveBtn');
//const $reviewBtn = document.querySelector('.reviewBtn');
const $prevBtn = document.querySelector('.prevBtn');
const $inputSpage = document.querySelector('.input-spage');
$saveBtn.addEventListener('click', btn_check);
//$reviewBtn.addEventListener('click', btn_check);
$prevBtn.addEventListener('click', btn_check);
$inputSpage.value = document.querySelector('.input-spage').value;
function btn_check(e){
    if(e.target.id === "saveBtn")
    {
//        const inputIsbn = document.querySelector('.input-isbn').value;
//        const isbn = inputIsbn.slice(0,10);
//         const url = "/book/"+isbn+"/save";
         const url = "/book/save";
         $infoForm.action = url;
         $infoForm.submit();

    }else if(e.target.id === "prevBtn"){
//          location.go(-1);
        const inputWord = document.querySelector('.input-word').value;
        console.log(inputWord);
        const url = "/book/search/"+inputWord;

        location.href = url;
    }
//    else if(e.target.id === "reviewBtn"){
//
//         const url = `/book/review`;
//         $infoForm.action = url;
//         $infoForm.submit();
//    }

//              const $infoForm = document.querySelector('.info-form')
//              const url = `/book/info`;
//              $infoForm.action = url;
//              $infoForm.submit();
//    }
}