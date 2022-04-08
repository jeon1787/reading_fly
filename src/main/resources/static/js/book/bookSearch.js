'use strict';

//      //dom 이벤트 : 댓글목록 불러오기
//      document.addEventListener('DOMContentLoaded', e=>{
//        console.log('DOM 생성');
//        const array_href = window.location.href.split('/');
//        console.log(array_href[5]);
////        array_href[3]
//        console.log(window.location.href);
//
//        if(array_href[5] != null){
//            const beforeKey = array_href[5];
//            notnull(beforeKey);
//        }else{
//            return;
//        }
////        const bnum = document.querySelector('.form').dataset.bnum;
////        list_f(bnum);
//      });


//상세화면 가기
const $infoClick = document.querySelector('.search-result');
$infoClick.addEventListener('click', e => {
    if (e.target.tagName != 'IMG') return;

    const inputWord = document.querySelector('.input-word');
    const inputIsbn = document.querySelector('.input-isbn');
    const inputTitle = document.querySelector('.input-title');
    const inputContents = document.querySelector('.input-contents');
    const inputAuthors = document.querySelector('.input-authors');
    const inputPublisher = document.querySelector('.input-publisher');
    const inputTranslators = document.querySelector('.input-translators');
    const inputThumbnail = document.querySelector('.input-thumbnail');
    const inputPublisher_dt = document.querySelector('.input-publisher_dt');

    const msg = event.target.closest('.imgList');

    console.log(msg);
    console.log(document.querySelector('.word').value);

    inputWord.value = document.querySelector('.word').value;
    inputIsbn.value = msg.dataset.isbn;
    inputTitle.value = msg.children[1].children[1].children[0].textContent;
    inputAuthors.value = msg.children[1].children[2].textContent;
    inputPublisher.value = msg.children[1].children[5].textContent;
    inputTranslators.value = msg.children[1].children[6].textContent;
    inputThumbnail.value = msg.children[0].attributes[0].value;
    inputPublisher_dt.value = msg.children[1].children[4].textContent
    inputContents.value = msg.children[1].children[3].attributes[1].textContent;



    const $infoForm = document.querySelector('.info-form')
    const url = `/book/info`;
    $infoForm.action = url;
    $infoForm.submit();
});


searchBtn.addEventListener('click', event => {
    let i = 0;
    let j = 0;
    $('div').remove('.imgList');
    $.ajax({
        method: "GET",
        url: "https://dapi.kakao.com/v3/search/book?target=title",
        data: { query: $(".word").val(), size: "50" },
        headers: { Authorization: "KakaoAK f75e6a117839fe46761b0b28cf75a7d1" }
    })
        .done(function (msg) {

            let authors;
            let translators;
            for (i = 0; i < msg.documents.length; i++) {
                authors = msg.documents[i].authors;
                authors = undifinded_remove_f(authors);
                translators = msg.documents[i].translators;
                translators = undifinded_remove_f(translators);
                $(".search-result").append('<div class="imgList" id="imgList" data-isbn="' + msg.documents[i].isbn + '" data-contents="' + msg.documents[i].contents + '"><img src="' + msg.documents[i].thumbnail + '"><div class="contentlist"><input type="hidden" value="' + msg.documents[i].isbn + '"></input><p><strong>' + msg.documents[i].title + '</strong></p><p>' + authors + '</p><input type="hidden" value="' + msg.documents[i].contents + '"></input><p>' + msg.documents[i].datetime.slice(0, 10) + '</p><p>' + msg.documents[i].publisher + '</p><p>' + translators + '</p></div></div>');
                //            $(".search-result").append('<div class="imgList" id="imgList" onclick="imgClick(event);" data-isbn="' + msg.documents[i].isbn + '" data-contents="' + msg.documents[i].contents + '"><img src="' + msg.documents[i].thumbnail + '"><div class="contentlist"><input type="hidden" value="' + msg.documents[i].isbn + '"></input><p><strong>' + msg.documents[i].title + '</strong></p><p>' + authors + '</p><input type="hidden" value="' + msg.documents[i].contents + '"></input><p>' + msg.documents[i].datetime.slice(0, 10) + '</p><p>' + msg.documents[i].publisher + '</p><p>' + translators + '</p></div></div>');
            }
            $(".word").val = null;
        });
});


//이전 검색어 존재유무
const keyword = document.getElementById('search-result').dataset.keyword;
if (keyword && keyword.length > 0) {
    word.value = keyword;
    searchBtn.click();
}

function undifinded_remove_f(obj) {
    let i = 0;
    let str = '';
    for (i = 0; i < obj.length; i++) {
        if (i != 0) {
            str += ', ';
            str += obj[i];
        } else {
            str += obj[i];
        }
    }
    if (str == '') {
        str = '-';
    }
    return str;
}
const key = $(".word").val();
function notnull(beforeKey) {
    key = beforeKey;
    let i = 0;
    let j = 0;
    $('div').remove('.imgList');
    $.ajax({
        method: "GET",
        url: "https://dapi.kakao.com/v3/search/book?target=title",
        data: { query: key, size: "50" },
        headers: { Authorization: "KakaoAK f75e6a117839fe46761b0b28cf75a7d1" }
    })
        .done(function (msg) {

            let authors;
            let translators;
            for (i = 0; i < msg.documents.length; i++) {
                authors = msg.documents[i].authors;
                authors = undifinded_remove_f(authors);
                translators = msg.documents[i].translators;
                translators = undifinded_remove_f(translators);
                $(".search-result").append('<div class="imgList" id="imgList" data-isbn="' + msg.documents[i].isbn + '" data-contents="' + msg.documents[i].contents + '"><img src="' + msg.documents[i].thumbnail + '"><div class="contentlist"><input type="hidden" value="' + msg.documents[i].isbn + '"></input><p><strong>' + msg.documents[i].title + '</strong></p><p>' + authors + '</p><input type="hidden" value="' + msg.documents[i].contents + '"></input><p>' + msg.documents[i].datetime.slice(0, 10) + '</p><p>' + msg.documents[i].publisher + '</p><p>' + translators + '</p></div></div>');
                //            $(".search-result").append('<div class="imgList" id="imgList" onclick="imgClick(event);" data-isbn="' + msg.documents[i].isbn + '" data-contents="' + msg.documents[i].contents + '"><img src="' + msg.documents[i].thumbnail + '"><div class="contentlist"><input type="hidden" value="' + msg.documents[i].isbn + '"></input><p><strong>' + msg.documents[i].title + '</strong></p><p>' + authors + '</p><input type="hidden" value="' + msg.documents[i].contents + '"></input><p>' + msg.documents[i].datetime.slice(0, 10) + '</p><p>' + msg.documents[i].publisher + '</p><p>' + translators + '</p></div></div>');
            }
            $(".word").val = null;
        });
}