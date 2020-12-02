function postAPI(url, data, successCallback){
  $.ajax({
      type : 'POST',
      url : url,
      dataType : 'json',
      data : data,
      contentType : "application/json; charset=UTF-8",
      processData: false,
      success : function(result) {
        // TODO: result
        successCallback(result);
      },
      error: function(request, status, error) {
        console.log("Error post Api state : " + status);
        console.log("Error post Api error : " + error);
      }
  }) // End Ajax Request
}