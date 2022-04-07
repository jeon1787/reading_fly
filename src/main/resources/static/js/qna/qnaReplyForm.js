'use strict';

//textArea => ck_editor 대체
ClassicEditor
      .create( document.querySelector( '#qContent' ), {
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

const $qna = document.querySelector('.wrap');
const page = $qna.dataset.page;

//등록
writeBtn?.addEventListener("click", e=>{
  writeForm.submit();
});
//목록
cancelBtn?.addEventListener("click",e=>{
   if(!confirm('사이트에서 나가시겠습니까? 변경사항이 저장되지 않을 수 있습니다')) return;
     const url = `/qna/list/${page}`;
     location.href = url;
});
