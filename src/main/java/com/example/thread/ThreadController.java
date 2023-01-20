package com.example.thread;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.comment.CommentService;
import com.example.entity.Comment;
import com.example.entity.Threads;

@Controller
@RequestMapping("/threads")
public class ThreadController {

	private ThreadService threadService;
	private CommentService commentService;

	@Autowired
	public ThreadController(ThreadService threadService,CommentService commentService) {
		this.threadService = threadService;
		this.commentService = commentService;
	}

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

	//スレッド詳細表示
	@GetMapping("/detail/{id}")
	public String detailThreads(@PathVariable(name = "id") Long id,@AuthenticationPrincipal UserDetails user, Model model) {
		Threads threads = threadService.get(id);
		List<Comment> comments = this.commentService.findAll();
		String userName = this.userDetail.getUsername();
		//スレッド詳細画面にタイムリーフで変数を送信
		model.addAttribute("thread", threads);
		model.addAttribute("comments",comments);
		model.addAttribute("userName",userName);

		return "view/thredDetail";
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
