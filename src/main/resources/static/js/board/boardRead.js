'use strict';

const $modifyBtn 	= document.getElementById('modifyBtn');
const $delBtn 		= document.getElementById('deleteBtn');
const $replyBtn 	= document.getElementById('replyBtn');
const $listBtn 		= document.getElementById('listBtn');

const $commentDelBtn 		= document.getElementById('commentDelBtn');
const $commentMoiBtn 		= document.getElementById('commentMoiBtn');

const handler = (res, e) => {
	//console.log(e);
	if(res.rtcd == '00'){
		const category = e.target.dataset.category;
		location.href = `/board/boardList?category=${category}`;
	}else{
		alert('댓글이 있어 삭제가 불가능합니다');
		return false;
	}
}

//수정된 댓글 내용 표시
const displayModifiedComments = res => {

	const $modifiedComments = document.getElementById('commentTextarea');

	if(res.rtcd == '00'){
	    $modifiedComments.textContent = res.data;
			const $commentTextarea = document.getElementById('commentTextarea');
			$commentTextarea.style.border="0";
			$commentTextarea.readOnly=true;

			$commentMoiBtn.innerHTML="수정하기";
			/*$commentMoiBtn.id = "commentMoiBtn";*/
			$commentMoiBtn.nextElementSibling.remove(); //삭제버튼 제거
			var deleteButton = document.createElement("button");
			deleteButton.className = 'btn btn-success';
			deleteButton.id = 'commentDelBtn';
			deleteButton.innerHTML = "삭제";
			$commentMoiBtn.parentNode.appendChild(deleteButton);

	  }else{
		  $modifiedComments.textContent = res.data;
	  }
	}

//게시글 수정
$modifyBtn?.addEventListener("click", e=>{
	const bnum = e.target.dataset.bnum;
	location.href = `/board/boardModify/${bnum}`;
});

//게시글 삭제
$delBtn?.addEventListener("click", e=>{
	const bnum = e.target.dataset.bnum;
	const url = `/board/${bnum}`;

	if(confirm('삭제하시겠습니까?')){
		request.delete(url)
					 .then(res=>res.json())
					 .then(res=>handler(res, e))
					 .catch(err=>console.log(err));
	}
});

//답글
$replyBtn?.addEventListener("click",e=>{
		const bnum = e.target.dataset.bnum;
	location.href=`/board/replyQnA/${bnum}`;
});

//목록
$listBtn?.addEventListener("click", e=>{
	const category = e.target.dataset.category;
	if(confirm('목록으로 돌아가시겠습니까?')){
		location.href = `/board/boardList`;
		}
});

//댓글삭제
$commentDelBtn?.addEventListener("click", e=>{
	const cnum = e.target.dataset.cnum;
	if(confirm('댓글을 삭제 하시겠습니까?')){
		location.href = `/board/comment/del/${cnum}`;
		}
});

//댓글수정
$commentMoiBtn?.addEventListener("click", e=>{

	console.log($commentMoiBtn.parentNode.nextElementSibling.firstElementChild
								.nextElementSibling.nextElementSibling);		//댓글내용창
	$commentMoiBtn.parentNode.nextElementSibling.firstElementChild
								.nextElementSibling.nextElementSibling.readOnly=false;	// readonly삭제
	$commentMoiBtn.parentNode.nextElementSibling.firstElementChild
								.nextElementSibling.nextElementSibling.style.border="solid";
	$commentMoiBtn.nextElementSibling.remove(); //삭제버튼 제거

	$commentMoiBtn.innerHTML="수정완료";
	/*$commentMoiBtn.id = "commentMoiOkBtn";*/
	var cancelButton = document.createElement("button");
	cancelButton.className = 'btn btn-success';
	cancelButton.id = 'commentModiCancel';
	cancelButton.innerHTML = "수정취소";
	$commentMoiBtn.parentNode.appendChild(cancelButton);


	//수정취소 버튼 클릭
	cancelButton.addEventListener("click", e=>{
		location.href = `/board/${bnum}`;
		});

	//수정완료 버튼 클릭
	$commentMoiBtn.addEventListener("click", e=>{
		console.log('수정완료 클릭!');

		const $modifiedComments = document.getElementById('commentTextarea');
		const $hiddenCid = document.getElementById('hiddenCid');
		const $hiddenCnum = document.getElementById('hiddenCnum');

	  //유효성 체크
    if($modifiedComments.value.trim().length == 0) {
			let $hiddens = document.querySelectorAll(".hidden");
  	  Array.from($hiddens).forEach(ele=>ele.classList.remove("hidden"));
  	  email.textContent = '댓글 내용이 없습니다';
  	  $modifiedComments.focus();
  	  $modifiedComments.select();
  	  return;
    }

		//ajax call!
		const url = '/board/modifyComment';

		const payload = {
			id : $hiddenCid.value,
			cnum : $hiddenCnum.value,
			modifiedContent: $modifiedComments.value
		};
		console.log(payload);
		request.patch(url,payload)

					 .then(res=>res.json())    //서버에서 클라이언트로 값을 전송할때 json형태의 문자열로 오게되는데 그것을 참조해서 쓰기엔 너무 복잡하다 따라서 json을 자바스크립트 객체형태로 변환해주는 기능
					 .then(res=>displayModifiedComments(res))
/*					 .catch(err=> {
						 displayError(err);
						 console.log(err);
					 });*/
				});
		});




