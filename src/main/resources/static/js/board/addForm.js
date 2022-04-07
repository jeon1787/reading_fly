'use strict';
//textArea => ck_editor 대체
ClassicEditor
		.create( document.querySelector( '#bcontent' ), {
		 plugin:['ListStyle','Markdown','MediaEmbed','MediaEmbedToolbar'],
			toolbar: {
				items: [
					'heading',
					'|',
					'underline',
					'bold',
					'italic',
					'link',
					'bulletedList',
					'numberedList',
					'todoList',
					'|',
					'outdent',
					'indent',
					'|',
					'imageInsert',
					'imageUpload',
					'blockQuote',
					'insertTable',
					'mediaEmbed',
					'markdown',
					'undo',
					'redo',
					'|',
					'highlight',
					'fontFamily',
					'fontColor',
					'fontBackgroundColor',
					'fontSize',
					'|',
					'htmlEmbed',
					'specialCharacters'
				]
			},
			language: 'en',
			image: {
				toolbar: [
					'imageTextAlternative',
					'imageStyle:full',
					'imageStyle:side'
				]
			},
			table: {
				contentToolbar: [
					'tableColumn',
					'tableRow',
					'mergeTableCells',
					'tableCellProperties',
					'tableProperties'
				]
			},
		})
		.then( editor => {
			window.editor = editor;
		} )
		.catch( error => {
			console.error( error );
		} );

//이벤트 막기(Capturing)
document.body.addEventListener('click', e=>{
  if(e.target.id == "addBtn") return;
  if(e.target.tagName == "A" || e.target.tagName == "BUTTON"){
    if(!confirm("작성을 종료하시겠습니까?")){
      //동일한 DOM 에 걸린 이벤트를 막기(a 태그)
      if (e.preventDefault) e.preventDefault();
      else e.returnValue = false;//IE 대응

      //동일한 DOM 에 걸린 이벤트를 막기(button 태그)
      if (e.stopImmediatePropagation) e.stopImmediatePropagation();
      else e.isImmediatePropagationEnabled = false;//IE 대응
    }
  }
}, true)//이벤트 캡쳐링(Capturing)

//등록 버튼(Bubbling)
addBtn.addEventListener('click', e=>{
  if(document.querySelector('.title').value.trim() == ''){
    alert("제목을 입력하세요.");
    return;
  }
  if(document.querySelector('.ck-content').textContent.trim() == ''){
    alert("본문을 입력하세요.");
    return;
  }
  const $formTag = document.querySelector('.addForm');
  const url = "/board/add";
  $formTag.action = url;
  $formTag.submit();
})

//목록 버튼(Bubbling)
listBtn.addEventListener('click', e=>{
  const url = "/board";
  location.href = url;
})