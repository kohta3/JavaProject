package com.example.comment;

import java.net.URI;
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
import org.springframework.web.util.UriComponentsBuilder;

import com.example.animeTitle.AnimeTitleService;
import com.example.category.CategoryService;
import com.example.entity.AnimeTitle;
import com.example.entity.Categories;
import com.example.entity.Comment;
import com.example.entity.Threads;
import com.example.security.A2ChannelUserDetails;
import com.example.thread.ThreadService;

@Controller
@RequestMapping("/comment")
public class CommentController {

	private CommentService commentService;
	private final CategoryService categoryService;
	private final AnimeTitleService animeTitleService;
	private final ThreadService threadsService;



	@Autowired
	public CommentController(ThreadService threadsService, CommentService commentService,CategoryService categoryService, AnimeTitleService animeTitleService) {
		this.commentService = commentService;
		this.animeTitleService = animeTitleService;
		this.categoryService = categoryService;
		this.threadsService = threadsService;
	}

	@PostMapping("/create")
	public String createComment(Comment comment,@AuthenticationPrincipal A2ChannelUserDetails loginUser, UriComponentsBuilder builder) {

		comment.setDateTime(LocalDateTime.now());
		comment.setUserId(loginUser.getUser().getId());
		this.commentService.save(comment);

		//スレッドのコメント数更新
		Threads thread = this.threadsService.get(comment.getThreadId());
		thread.setCommentSum(thread.getCommentSum() + 1);
		this.threadsService.save(thread);

		URI location = builder.path("/threads/detail/" + comment.getThreadId()).build().toUri();

		return "redirect:"+ location.toString();
	}

	@GetMapping("/delete/{id}")
	public String detailCategory(@PathVariable(name = "id") Long id, Model model) {
		//スレッド情報のコメント数ー１
		Comment comment = this.commentService.get(id);
		Threads thread = this.threadsService.get(comment.getThreadId());
		thread.setCommentSum(thread.getCommentSum() - 1);
		this.threadsService.save(thread);

		//コメント削除
		this.commentService.delete(id);
		return "redirect:/threads/detail/" + comment.getThreadId();
	}

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
}
