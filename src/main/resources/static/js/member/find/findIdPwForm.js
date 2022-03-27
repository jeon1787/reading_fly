'use strict';

function div_show(selectList) {
      var obj1 = document.getElementById("find_id_form"); 
      var obj2 = document.getElementById("find_pw_form"); 
      
    
      if( selectList == "0" ) { 
          obj1.style.display = "block";    
          obj2.style.display = "none";
          
      } else {
          obj1.style.display = "none";    
          obj2.style.display = "block";
          
          
      } 
    }
  
  
  
    // findIdBtn.addEventListener('click', findById);
  
    //   function findById(e){
    //     const data ={};
    //     data.name = nameIdTxt.value;
    //     data.email = emailIdTxt.value;

    //     if(data.name == "" || data.email == "") {
    //       alert('데이터를 입력해주세요')
    //       return;
    //     }

    //       fetch('/memberexist/exist/findid',{            
    //     method:'PUT',
    //     headers:{'Content-Type':'application/json'},
    //     body:JSON.stringify(data)
    //   }).then(res=>res.json())
    //     .then(res=>{console.log(res);
    //       findedId.innerHTML = res.data;}
    //     )
    //     .catch(err=>console.error('Err:',err));
  
    //   }

  
    //   findPwBtn.addEventListener('click', findByPw);
  
  
    //   function findByPw(e){
    //       const data ={};
    //       data.id = idPwTxt.value;
    //   data.name = namePwTxt.value;
    //   data.email = emailPwTxt.value;

    //   if(data.id == "" || data.name == "" || data.email == "") {
    //     alert('데이터를 입력해주세요')
    //     return;
    //   }
  
    //       fetch('/memberexist/exist/findpw',{
    //     method:'PUT',
    //     headers:{'Content-Type':'application/json'},
    //     body:JSON.stringify(data)
    //   }).then(res=>res.json())
    //     .then(res=>{console.log(res);
    //       findedPw.innerHTML = res.data;}
    //     )
    //     .catch(err=>console.error('Err:',err));
  
  
    //   }
  
  
  
  