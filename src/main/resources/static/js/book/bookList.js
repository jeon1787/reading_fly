'use strict';
const $emptyImg = document.getElementById('emptyImg');


$emptyImg.addEventListener('click', event => {
    location.href = `/book/search`;
})

// const $bookImg = document.getElementById('bookImg');
// $bookImg.addEventListener('click', event => {
//     console.log(event);
//     console.log(book);
//     const isbn = event.target.dataset.isbn;
//     const url = '/book/{' + isbn + '}/update';
//     lodation.href = url;
// })
    
// bookImg.addEventListener('mouseOver', event => {
//     var positionX = $bookImg.position().left;
//     var positionY = $bookImg.position().top;
//     $bookImg.append('<button id="deleteImg" style="width: 20px; height: 20px; left:' + positionX + 'top:' + positionY + '">x</button>');
// })

// bookImg.addEventListener('mouseleave', event => {
//     $("#delete").remove();
// })

// const deleteImg = document.getElementById('deleteImg');
// deleteImg.addEventListener('click', event => {
//     const isbn = document.getElementById('list').isbn.value;
//     const url = `/book/{' + isbn + '}/delete`;
//     location.href = url;
// })


// function deleteClick(){
//  if(confirm('삭제하시겠습니까?')){
//    const isbn = document.getElementById('BookForm').isbn.value;
//    const url = `/book/${isbn}/delete`;
//    location.href = url;
//    }
//  }