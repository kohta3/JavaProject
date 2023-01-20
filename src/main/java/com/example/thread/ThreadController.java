package com.example.thread;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.animeTitle.AnimeTitleService;
import com.example.category.CategoryService;
import com.example.entity.Categories;
import com.example.entity.Threads;

@Controller
@RequestMapping("/threads")
public class ThreadController {

	private ThreadService threadService;
	private CategoryService categoryService;
	private AnimeTitleService animeTitleService;


	public ThreadController(ThreadService threadService, CategoryService categoryService, AnimeTitleService animeTitleService) {
		this.threadService = threadService;
		this.categoryService = categoryService;
		this.animeTitleService = animeTitleService;
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

	//トップページ
	@GetMapping("/home")
	public String toppages() {
		return "view/toppage";
	}

	/**
	 * スレ投稿ページ
	 * @param Model
	 * @return スレッド新規投稿ページ
	 */
	@GetMapping("/postThred")
	public String showNewThred(Model model) {
		//新しいスレッド情報
//		Threads threads = new Threads();
		NewThreadForm threadsForm = new NewThreadForm();
		//カテゴリ情報取得
		List<Categories> categories = this.categoryService.listAll();

		//画面に渡す
		model.addAttribute("threadsForm", threadsForm);
		model.addAttribute("categories", categories);

		return "view/threadPosting2";
	}

	/**
	 * スレッド投稿処理
	 * @param animetitle_word
	 * @param 新規スレッド情報 threads
	 * @return view/threadDetail
	 *@RequestParam("animeTitle") String animeTitle
	 */
	@PostMapping("/postThred")
	public String createThread(NewThreadForm threadsForm) {
		//アニメIDの取得,登録
		String animeTitle = threadsForm.getAnimeTitle();
		Threads threads = threadsForm.getThreads();
		Long animeId = this.animeTitleService.searchId(animeTitle, threads.getCategoryId());

		//スレッドの中のアニメIDの登録
		threads.setAnimeId(animeId);
		threads.setUserId(1L);
		threads.setCommentSum(1L);
		threads.setDateTime(LocalDateTime.now());

		//スレッドの登録
		this.threadService.save(threads);
		return "redirect:/threads";
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
