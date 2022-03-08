'use strict';

const $cancelBtn 	= document.getElementById('cancelBtn');

//취소
$cancelBtn?.addEventListener("click", e=>{
	const category = e.target.dataset.category;
		if(confirm('작성을 취소하시겠습니까?')){
		location.href = `/board/boardList`;
			}
});


