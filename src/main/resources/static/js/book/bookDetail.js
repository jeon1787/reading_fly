'use strict';
$rangeBar = document.getElementById('rangeBar');
$crange = document.getElementById('rangeBar').value;

$startPage = document.getElementById('startPage').value;
$cpage = document.getElementById('startPage').value;
$dpage = document.getElementById('endPage').value;
$maxPage = document.getElementById('rangeBar').max;

    $rangeBar.addEventListener('change', event => {
        $crange.value = $rangeBar.value;
        $cpage.value = $rangeBar.value;
    });

    $cpage.addEventListener('keyup', event => {
        if(event.keyCode == 13){
            $cpage.value = $startPage.value;
            $rangeBar.value = $startPage.value;
        }
    });

    $dpage.addEventListener('keyup', event => {
        if(event.keyCode == 13){
            $maxPage.value = $dpage.value;
            $maxPage.value = $dpage.value;
        }
    });

    $cpage.addEventListener('action')

    const $updateBtn = document.getElementById('updateBtn');
    $updateBtn.addEventListener('click', event => {
        const frm = documnt.getElementById('updateForm');
        const isbn = frm.id.value;
        const url = `/book/${isbn}/detail`;

        frm.action = url;
        frm.submit();
    });

    const $listBtn = document.getElementById('listBtn');
    $listBtn.addEventListener('click', event => {
        location.href = '/book/all';
    });