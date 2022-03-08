'use strict';

const $writeBtn = document.getElementById('WriteBtn');
const $searchBtn 	= document.getElementById('searchBtn');
const $searchType = document.getElementById('searchType');
const $keyword 		= document.getElementById('keyword');

//글작성
$writeBtn.addEventListener("click",e=>{
  console.log("클릭");
	const category = e.target.dataset.category;
	location.href = `/board/boardWrite`;
});

//검색 버튼 클릭시
$searchBtn.addEventListener('click',e=>{

	//검색어입력유무
	if($keyword.value.trim().length == 0){
		alert('검색어를 입력하세요');
		$keyword.focus();
		$keyword.select();
		return false;
	}

		const category = e.target.dataset.category;
		location.href = `/board/boardList/1/${$searchType.value}/${$keyword.value}?category=${category}`;
});

//검색입력창 엔터키 눌렀을때
$keyword.addEventListener('keydown',e=>{
	console.log(e.key);
	if(e.key == 'Enter'){ //엔터키
		e.preventDefault();
		//검색어입력유무
		if(e.target.value.trim().length == 0){
			alert('검색어를 입력하세요');
			e.target.focus();
			e.target.select();
			return false;
		}

		const category = e.target.dataset.category;
		location.href = `/board/boardList/1/${$searchType.value}/${$keyword.value}?category=${category}`;

	}
});
