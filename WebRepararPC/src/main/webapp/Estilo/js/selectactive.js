$("#campo1").change(function() {
      if($("#campo1").val() !== "0"){
        $('#campo2').prop('disabled', false);
      }else{
        $('#campo2').prop('disabled', 'disabled');
//        $('#diagnostico2').prop('disabled', 'disabled');
//        $('#diagnostico3').prop('disabled', 'disabled');
      }
    });
    
    $("#diagnostico1").change(function() {
      if($("#campo1").val() !== "0"){
        $('#campo2').prop('disabled', false);
      }else{
        $('#campo2').prop('disabled', 'disabled');
//        $('#diagnostico3').prop('disabled', 'disabled');
      }
    });