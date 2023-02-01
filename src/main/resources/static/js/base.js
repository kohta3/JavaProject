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

//アニメタイトル予測変換
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

var obj1 = document.getElementById("selfile");

obj1.addEventListener("change", function(evt){
  var file = evt.target.files;
  var reader = new FileReader();

  //dataURL形式でファイルを読み込む
  reader.readAsDataURL(file[0]);

  //ファイルの読込が終了した時の処理
  reader.onload = function(){
    var dataUrl = reader.result;

    //読み込んだ画像とdataURLを書き出す
    document.getElementById("threadPostImg").innerHTML = "<img src='" + dataUrl + "'>";
  }
},false);


$("#clearImg").on('click',function(){
	$("#threadPostImg").empty();
	$("#selfile").val(null);
})