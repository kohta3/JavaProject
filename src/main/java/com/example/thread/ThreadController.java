package com.example.thread;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.FirebaseService;
import com.example.animeTitle.AnimeTitleService;
import com.example.block.BlockService;
import com.example.category.CategoryService;
import com.example.comment.CommentService;
import com.example.entity.AnimeTitle;
import com.example.entity.Categories;
import com.example.entity.Comment;
import com.example.entity.Threads;
import com.example.follow.FollowService;
import com.example.security.A2ChannelUserDetails;

@Controller
@RequestMapping("/threads")
public class ThreadController {

	private ThreadService threadService;
	private CommentService commentService;
	private CategoryService categoryService;
	private AnimeTitleService animeTitleService;
	private FollowService followService;
	private BlockService blockService;
	private FirebaseService firebaseService;

	@Autowired
	public ThreadController(BlockService blockService,FollowService followService,ThreadService threadService, CategoryService categoryService, AnimeTitleService animeTitleService ,CommentService commentService,FirebaseService firebaseService) {
		this.threadService = threadService;
		this.categoryService = categoryService;
		this.animeTitleService = animeTitleService;
		this.commentService = commentService;
		this.followService = followService;
		this.blockService = blockService;
		this.firebaseService =firebaseService;
	}

	//左サイドバーにカテゴリ情報を送る
	@ModelAttribute("categories")
	public List<Categories> leftSideMenu() {
		List<Categories> categories = this.categoryService.listAll();
		return categories;
	}

	//右サイドバーにアニメタイトル情報を送る
	@ModelAttribute("animeTitles")
	public List<AnimeTitle> rightSideMenu() {
		List<AnimeTitle> animeTitles = this.animeTitleService.listAll();
		return animeTitles;
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

		//
		//取得したスレッド情報を画面に渡す
		model.addAttribute("threads", threads);



		return "view/toppage";
	}

	//スレッド詳細表示
	@GetMapping("/detail/{id}")
	public String detailThreads(@PathVariable(name = "id") Long id, Model model, @AuthenticationPrincipal A2ChannelUserDetails loginUser) {
		Threads threads = threadService.get(id);
		List<Comment> comments = this.commentService.commentMatchingTheThread(id);
		Comment comment = new Comment();

		//ログイン情報からフォローリスト,ブロックリスト取得
		//もしログイン情報があった場合処理実行
		if(loginUser != null) {
			//フォローリスト取得
			List<Long> followUserList = this.followService.listUserId(loginUser.getUser().getId());
			//ブロックリスト取得
			List<Long> blockUserList = this.blockService.listUserId(loginUser.getUser().getId());
			//フォローされている人のリスト
			List<Long> passiveFollowList = this.followService.passiveFollowUserId(loginUser.getUser().getId());

			model.addAttribute("follows", followUserList);
			model.addAttribute("blocks", blockUserList);
			model.addAttribute("passiveFollowers", passiveFollowList);
			model.addAttribute("loginUser", loginUser.getUser().getId());
		}
		//スレッド詳細画面にタイムリーフで変数を送信
		model.addAttribute("thread", threads);
		model.addAttribute("comments",comments);
		model.addAttribute("comment",comment);

		return "view/thredDetail";
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
	public String showNewThred(Model model, @AuthenticationPrincipal A2ChannelUserDetails loginUser) {
		//新しいスレッド情報
//		Threads threads = new Threads();
		NewThreadForm threadsForm = new NewThreadForm();
		//カテゴリ情報取得
		List<Categories> categories = this.categoryService.listAll();

		//画面に渡す
		model.addAttribute("threadsForm", threadsForm);
		model.addAttribute("categories", categories);
		model.addAttribute("loginUser", loginUser.getUser());

		return "view/threadPosting2";
	}

	/**
	 * スレッド投稿処理
	 * @param animetitle_word
	 * @param 新規スレッド情報 threads
	 * @return view/threadDetail
	 *@RequestParam("animeTitle") String animeTitle
	 * @throws IOException
	 */
	@PostMapping("/postThred")
	public String createThread(NewThreadForm threadsForm, @AuthenticationPrincipal A2ChannelUserDetails loginUser, @RequestParam(name="upload_file") MultipartFile multipartFile){
		//画像の登録
		System.out.println("testtesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttest");
		System.out.println(multipartFile);
		System.out.println("testtesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttest");
		String filePath = null;
		try {
			filePath = firebaseService.uploadFile(multipartFile);
		} catch (IOException e) {
			e.printStackTrace();
		}

		//アニメIDの取得,登録
		String animeTitle = threadsForm.getAnimeTitle();
		Threads threads = threadsForm.getThreads();
		Long animeId = this.animeTitleService.searchId(animeTitle, threads.getCategoryId());

		//スレッドの中のアニメIDの登録
		threads.setAnimeId(animeId);
		threads.setUserId(loginUser.getUser().getId());
		threads.setCommentSum(1L);
		threads.setDateTime(LocalDateTime.now());
//		threads.setImage(filePath);

		//スレッドの登録
		this.threadService.save(threads);
		return "redirect:/threads";
	}

	//スレ一覧(カテゴリー絞り込み)
	@GetMapping("/threadsCategory/{categoryId}")
	public String showThreadsCategory(@PathVariable Long categoryId, Model model, @RequestParam(name = "order" ,required = false) String order) {
		//カテゴリIDからカテゴリ情報を取得
		Categories category = this.categoryService.findById(categoryId);
		//カテゴリIDから該当のスレッド情報のリストを取得
		List<Threads> threads = this.threadService.findByCategory(categoryId, order);
		//画面に上記情報を送る
		model.addAttribute("threads", threads);
		model.addAttribute("category", category);
		return "view/thredCategory";
	}

	//スレ一覧(アニメタイトル絞り込み)
	@GetMapping("/threadsAnimeTitle/{animeTitleId}")
	public String showThreadAnimeTitles(@PathVariable Long animeTitleId, Model model, @RequestParam(name = "order" ,required = false) String order) {
		//アニメタイトル情報取得
		AnimeTitle animeTitle = this.animeTitleService.findById(animeTitleId);
		//スレッド情報取得
		List<Threads> threads = this.threadService.findByAnimeTitle(animeTitleId, order);
		//画面に上記情報を送る
		model.addAttribute("animeTitle", animeTitle);
		model.addAttribute("threads", threads);
		return "view/threadAnimeTitle";
	}

	/**
	 * スレ一覧(タイトル絞り込み)
	 * @param ＠requestParam keyword
	 * @param model
	 * @return スレッド検索結果画面
	 */
	@GetMapping("/threadTitle")
	public String thredTitle(@RequestParam String keyword, @RequestParam(name = "order" ,required = false) String order, Model model) {
		//スレッド検索
		List<Threads> threads = this.threadService.findByTitle(keyword, order);
		//画面に情報を渡す
		model.addAttribute("threads", threads);
		model.addAttribute("keyword", keyword);
		return "view/thredTitle";
	}

	@GetMapping("/thredDetail")
	public String threDetail() {
		return "view/thredDetail";
	}

}
