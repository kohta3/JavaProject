package com.example.comment;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
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
	public String createComment(@Validated Comment comment, BindingResult result, RedirectAttributes ra, @AuthenticationPrincipal A2ChannelUserDetails loginUser, UriComponentsBuilder builder) {

		comment.setDateTime(LocalDateTime.now());
		comment.setUserId(loginUser.getUser().getId());

		if(result.hasErrors()) {
			//正しい値が入力されているか
			List<String> errorList = new ArrayList<String>();
			//TODO 以下確認用コードは削除する
			for(ObjectError error : result.getAllErrors()) {
				errorList.add(error.getDefaultMessage());
			}
			if(comment.getId() == null) {
				ra.addFlashAttribute("validationError", errorList);
				return "redirect:/threads/detail/" + comment.getThreadId();
			}
		}

		this.commentService.save(comment);

		//スレッドのコメント数更新
		Threads thread = this.threadsService.get(comment.getThreadId());
		thread.setCommentSum(thread.getCommentSum() + 1);
		this.threadsService.save(thread);

		URI location = builder.path("/threads/detail/" + comment.getThreadId()).build().toUri();

		return "redirect:"+ location.toString()+"#thredComment";
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
