$("#userUpdate").hide();

$("#update").on('click',function (element){
	$("#userUpdate").show();
});

$("#select-order").change(function(){
	$(".selectBox-top").submit();
});