'use strict';
//
//document.addEventListener('DOMContentLoaded', event =>{
//    console.log("돔 생성");
//
//    const id = document.querySelector('.form').dataset.id;
//    list_f(id);
//});
//
//function list_f(id){
//    console.log("리스트호출");
//
//    fetch(`http://localhost:9080/api/book/${id}/list`, {method:'GET'})\
//    .then(res => res.json())
//    .then(res => {
//    displayBooks(res.data);
//    })
//    .catch(err => console.error('Err:', err))
//}
//
//function displayBooks(books){
//    const id = document.querySelector('.form').dataset.id;
//
//    let content = ``;
//    books.forEach(item => {
//        console.log("출력");
//        content +=
//               `<div class="item">
//                  <div class="item-header">
//                    <input class="cnum" type="hidden" value="${item.cnum}">
//                    <input class="cid" type="hidden" value="${item.cid}">
//                    <span>${comment.nickname}</span>
//                    <span><button class="editBtn" href="">수정</button><button class="delBtn" href="">삭제</button></span>
//                  </div>
//                  <div class="item-content" style="white-space:pre-wrap;">${item.ccontent}</div>
//                  <div class="item-footer"><span>${item.cudate}</span></div>
//                </div>`;
//    }
//
//    });
//    const $itemlist = document.querySelector('.detail-list');
//    $itemlist.innerHTML = content;
//}

//렌지바의 처음값은 dpage를 가져오기
const $rangeBar = document.querySelector('.range-bar');
let $range = document.querySelector('.range-bar').value;

//start페이지 처음값은 dpage를 가져오기
const $startPage = document.querySelector('.start-page');
let $start = document.querySelector('.start-page').value;

//end페이지 처음값은 spage를 가져오기
const $endPage = document.querySelector('.end-page');
let $end = document.querySelector('.end-page').value;

//렌지바를 움직이면 그 값이 startpage에 들어가기
$rangeBar.addEventListener('input', event => {
    console.log("$range:" + $range);
    $startPage.value = $rangeBar.value;
    console.log("$start:" + $start);
});

//startpage를 입력하고 엔터를 치면 렌지바의 값이 바뀌기
$startPage.addEventListener('keyup', event => {
    if (event.keyCode == 13) {
        console.log("$start:" + $start);
        $rangeBar.value = $startPage.value;
        console.log("$range:" + $range);
    }
});
//endpage를 입력하면 렌지바의 max값이 바뀌기
//endpage를 입력하면 spage 바꿔주기

$endPage.addEventListener('keyup', event => {
    if (event.keyCode == 13) {
        console.log("$end:" + $end);
        $rangeBar.max = $endPage.value;
        console.log("$rangeBar.max:" + $rangeBar.max);
        
    }
});

const $detailBtn = document.querySelector('.detail-btn');
$detailBtn.addEventListener('click', event => {
    const frm = document.querySelector('.detail-form');
    console.log("frm:" + frm);

    const isbn = frm.dataset.isbn;
    console.log("isbn:" + isbn);
    const url = `/api/book/${isbn}/save`;
    alert("멈춰");

    frm.action = url;
    frm.submit();
});

const $listBtn = document.querySelector('.list-btn');
$listBtn.addEventListener('click', event => {
    location.href = '/book/list';
});

//const $detailBtn = document.querySelector('.detail-btn');
//$detailBtn.addEventListener('click', create_f);
//
//function create_f(e){
//    const data = {};
//    data.ddate = date.value;
//    data.spage = endPage.value;
//    data.dpage = startPage.value;
//
//    fetch('/api/book/${isbn}/save',{
//        method:'POST',
//        headers: { 'Content-Type':'application/json'},
//        body: JSON.stringify(data)})  // js객체 => json포맷 문자열 변환
//            .then(res=>res.json())         // json포맷 문자열 => js객체 변환
//            .then(res=>{console.log(res); list_f(); })
//            .catch(err=>{console.error('Err:',err)});
//        console.log('after fetch');
//}
////상품목록 가져오기
//function list_f(e){
//
//  fetch('/api/book/${id}/list',{
//    method:'GET'
//  }).then(res=>res.json())
//    .then(res=>{printItemList(res);})
//    .catch(err=>{console.error('Err:',err)});
//}
//
////상품목록 출력
//function printItemList(res){
//  console.log(res);
//  let html = '';
//  if(res.rtcd === '00'){  //목록이 있는 경우
//    res.data.forEach(item => {
//      html += `<p>`;
//      html += `기록날짜:${item.ddate},기록페이지:${item.dpage}, 총페이지:${item.spage}`;
//      // html += `<button data-item-id='${item.id}' onclick='delItem(event)'>삭제</button>`;
//      html += `</p>`;
//    });
//  }else if(res.rtcd === '02'){ //목록이 없는 경우
//    html = '';
//  }else{
//    alert(res.rtmsg);
//  }
//  itemList.innerHTML = html;
//}