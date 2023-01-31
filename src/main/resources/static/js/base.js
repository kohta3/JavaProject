$("#userUpdate").hide();

$("#update").on('click', function(element){
	$("#userUpdate").show();
	var userCategories = $('.userCates').each(function(index, ele){
		var userCategory = ($(ele).attr("value"));
		$('#category'+ userCategory).attr("checked", "true");
	});

});

$("#select-order").change(function(){
	$(".selectBox-top").submit();
});