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

function searchTitle(title) {
	let url ='https://api.annict.com/v1/works?access_token=pzj3Fri6UyklcwKv5VU4m4EVSURmFraLAlBpx0VsR0c&per_page=30&filter_title='+title;
	$.ajax({
	    type: 'get',
	    url: url
	})
	.done(function(response) {
		$('#example').empty();
		$.each(response['works'],(index,val)=>{
			if(before!==response['works']){
				console.log(before!==response['works']);
				$('#example').append('<option>'+val['title']+'</option>');
			}
			var before = val['title'];
		});
	})
	.fail(function() {
		console.log("予測ワードがありません");
	});
}
