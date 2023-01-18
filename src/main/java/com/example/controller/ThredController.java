package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class ThredController {

	//トップページ
	@GetMapping
	public String toppage() {
		return "view/toppage";
	}

	//スレ投稿ページ
	@GetMapping("/postThred")
	public String postThred() {
		return "view/threadPosting";
	}

	//カテゴリー一覧
	@GetMapping("/Category")
	public String Category() {
		return "view/category";
	}

	//スレ一覧(カテゴリー絞り込み)
	@GetMapping("/thredCategory")
	public String thredCategory() {
		return "view/thredCategory";
	}

	//スレ一覧(タイトル絞り込み)
	@GetMapping("/thredTitle")
	public String thredTitle() {
		return "view/thredTitle";
	}

	//スレ詳細
	@GetMapping("/thredDetail")
	public String threDetail() {
		return "view/thredDetail";
	}

}
