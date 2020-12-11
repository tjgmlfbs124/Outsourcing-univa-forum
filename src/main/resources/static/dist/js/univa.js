// 수정요청중에 선택했을때,
function modifyApply(idx){
  var formData = new FormData();
  formData.append("id",idx)
  postAPI("/forum/main/modifyApply",formData, function(result){
     switch(result){
       case "ok":
         alert("변경되었습니다.");
         location.href="/forum/mypage?type=update_request"
         break;
       case "error" :
        alert("오류가 발생하였습니다.")
        break;
     }
   });
}

// 삭제요청
function requestRemove(idx){
  if(!idx) return;
  var formData = new FormData();
  formData.append("id",idx);
  postAPI("/forum/main/remove", formData , function(result){
     switch(result){
       case "ok":
         alert("삭제요청이 되었습니다.");
         window.location.reload();
         break;
       case "error" :
        alert("오류가 발생하였습니다.")
        break;
     }
   });
}

// 하단 Content 변경
function switchContent(target, url, callback){
  $("#"+target).load(url);
  callback(url);
}

// 검색결과를 가지고 restful api
function search(){
  var inputText = $("#input-search").val();
  var subjects = [];
  if(inputText=="") {
    alert("키워드를 입력해주세요."); return;
  }
  switchContent("result-row",
   "/forum/main/board?title="+inputText+"&min=0&max=10&sort=date",
   function (url){
     loadContent.push(url);
   });
}
