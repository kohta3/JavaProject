package com.example.thread;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.entity.Threads;

@Controller
@RequestMapping("/threads")
public class ThreadController {

	private ThreadService threadService;

	@Autowired
	public ThreadController(ThreadService threadService) {
		this.threadService = threadService;
	}

	//トップページ
	/**
	 * スレッド一覧ページ
	 * @param oreder
	 * @param model
	 * @return view/toppage
	 */
	@GetMapping
	public String showThreadsAll(Model model, @RequestParam(required = false) String order) {
		//スレッド一覧を取得
		List<Threads> threads = this.threadService.listAll(order);


		//取得したスレッド情報を画面に渡す
		model.addAttribute("threads", threads);
		return "view/toppage";
	}

	//トップページ
	@GetMapping("/home")
	public String toppages() {
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

	@GetMapping("/thredDetail")
	public String threDetail() {
		return "view/thredDetail";
	}

}
