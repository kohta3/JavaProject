package com.example.follow;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.animeTitle.AnimeTitleService;
import com.example.block.BlockService;
import com.example.category.CategoryService;
import com.example.entity.AnimeTitle;
import com.example.entity.Block;
import com.example.entity.Categories;
import com.example.entity.Follow;
import com.example.entity.User;
import com.example.security.A2ChannelUserDetails;
import com.example.user.UserService;

@Controller
@RequestMapping("/follows")
public class FollowController {

	private final FollowService followService;
	private final CategoryService categoryService;
	private final AnimeTitleService animeTitleService;
	private final UserService userService;
	private final BlockService blockService;

	public FollowController(BlockService blockService ,FollowService followService, AnimeTitleService animeTitleService, CategoryService categoryService, UserService userService) {
		this.followService = followService;
		this.animeTitleService = animeTitleService;
		this.categoryService = categoryService;
		this.userService = userService;
		this.blockService = blockService;
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
	//----------------共通部分に送る情報ここまで

	/**
	 * フォローユーザー登録(実際につかうメソッドはここだけ）
	 * @param loginuser
	 * @param followId
	 * @return フォロー前の画面
	 */
	@PostMapping("/save")
	public String createFollow(RedirectAttributes ra, @RequestParam("followId") Long followId ,@RequestParam("url") String url, @AuthenticationPrincipal A2ChannelUserDetails loginUser) {
		//ブロック情報取得
		Block activeBlock = this.blockService.getByUserIdAndBlockId(loginUser.getUser().getId(), followId);
		Block passiveBlock = this.blockService.getByUserIdAndBlockId(followId, loginUser.getUser().getId());
		//もし自分からのブロック情報があったら登録できない
		if(activeBlock != null) {
			ra.addFlashAttribute("error_message", "フォローする場合は、先にブロックを外してください。");
			return "redirect:" + url;
		}
		//もし相手からのブロック情報があったら登録できない
		if(passiveBlock != null) {
			ra.addFlashAttribute("error_message", "現在フォローできません");
			return "redirect:" + url;
		}
		//フォロー情報登録実行
		Follow follow = new Follow();
		follow.setUserId(loginUser.getUser().getId());
		follow.setFollowId(followId);
		this.followService.save(follow);

		return "redirect:" + url;
	}

	/**
	 * フォロー情報削除
	 * @param model
	 * @param loginUser
	 * @return
	 */
	@PostMapping("/delete")
	public String deleteFollow(@RequestParam("followId") Long followId, @RequestParam("url") String url, @AuthenticationPrincipal A2ChannelUserDetails loginUser) {
		Follow follow = this.followService.getByUserIdAndFollowId(loginUser.getUser().getId(), followId);
		this.followService.deleteFollow(follow);
		return "redirect:" + url;
	}



	/*
	 * フォロー情報取得
	 * 画面に渡す
	 */
	@GetMapping
	public String followList(Model model, @AuthenticationPrincipal A2ChannelUserDetails loginUser) {
		List<Follow> followList = this.followService.listAll(loginUser.getUser().getId());
		model.addAttribute("follows", followList);
		List<User> userList = this.userService.listAll();
		model.addAttribute("users", userList);
		return "view/test";
	}
}
