package com.example.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.animeTitle.AnimeTitleService;
import com.example.category.CategoryService;
import com.example.entity.AnimeTitle;
import com.example.entity.Categories;
import com.example.entity.Follow;
import com.example.entity.User;
import com.example.entity.UserCategories;
import com.example.follow.FollowService;
import com.example.security.A2ChannelUserDetails;
import com.example.userCategories.UserCategoriesService;


@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final FollowService followService;
	private final CategoryService categoryService;
	private final AnimeTitleService animeTitleService;
	private final UserCategoriesService userCategoriesService;

    @Autowired
    public UserController(UserService userService,CategoryService categoryService, AnimeTitleService animeTitleService,UserCategoriesService userCategoriesService, FollowService followService) {
        this.userService = userService;
        this.categoryService = categoryService;
		this.animeTitleService = animeTitleService;
		this.userCategoriesService = userCategoriesService;
		this.followService = followService;

    }

    /**
     * 新規登録画面表示
     *
     * @param model
     * @return ユーザー新規登録画面
     */
    @GetMapping("/new")
    public String newUser(Model model) {
        //新規登録用に、空のユーザー情報作成
        User user = new User();
        //カテゴリーを一覧で取得してくる
        List<Categories> categories = this.categoryService.listAll();
        model.addAttribute("user", user);
        model.addAttribute("categories", categories);
        return "users/user_form";
    }

    /**
     * 新規登録処理
     *
     * @param user ユーザー情報
     * @param ra
     * @return スレッド一覧画面
     */
    @PostMapping("/save")
    public String saveUser(User user, RedirectAttributes ra) {
    	//入力された文字数のチェック
        if (!userService.isValid(user.getEmail(), user.getName())) {
            ra.addFlashAttribute("error_message", "メールアドレスまたはユーザー名の文字数がオーバーしています");
            return "redirect:/users/new";
        }

        //ユーザー情報のユーザー名重複チェック
        if (!userService.UserNamecheckUnique(user)) {
            ra.addFlashAttribute("error_message", "既に使用されているユーザー名です。");
            return "redirect:/users/new";
        }

        //ユーザー情報のメールアドレス重複チェック
        if (!userService.UserEmailcheckUnique(user)) {
            ra.addFlashAttribute("error_message", "既に使用されているメールアドレスです");
            return "redirect:/users/new";
        }

        //ユーザー情報の登録
        userService.save(user);
        // 登録成功のメッセージを格納
        ra.addFlashAttribute("success_message", "ユーザーの新規登録に成功しました");
        return "redirect:/threads";
    }

    @GetMapping("/recommend")
    private String RecommendUser(Model model,@AuthenticationPrincipal A2ChannelUserDetails loginUser) {

    	List<UserCategories> userCategories = this.userCategoriesService.findByUserId(loginUser.getUser().getId());
    	List<UserCategories> userList;
    	for (UserCategories userCategory : userCategories) {
    		System.out.println(userCategory);
//			userList = this.userCategoriesService.findByCategoryId(Long.parseLong(userCategory));
		}

    	model.addAttribute("loginUser",loginUser.getUser().getName());
		model.addAttribute("userCategories",userCategories);

    	return "users/recommend";
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

    /**
     * マイページ画面
     *
     * @param user マイページ
     * @param ra
     * @return マイページ画面
     */
    @GetMapping("/mypage")
    public String MyPages(Model model, @AuthenticationPrincipal A2ChannelUserDetails loginUser) {

    	List<Follow> followList = this.followService.listAll(loginUser.getUser().getId());
		model.addAttribute("follows", followList);
    	model.addAttribute("loginUser", loginUser.getUser());
    	return "users/mypage";
    }

    /**
     * ユーザー情報編集画面
     *
     * @param user ユーザー情報
     * @param ra
     * @return 編集画面
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable(name = "id") Long id, Model model, RedirectAttributes ra, @AuthenticationPrincipal A2ChannelUserDetails loginUser) {
        try {
            //ユーザーIDに紐づくユーザー情報取得
            User user = userService.get(id);
            List<Categories> categories = this.categoryService.listAll();
          	List<UserCategories> userCategories = this.userCategoriesService.findByUserId(loginUser.getUser().getId());
            model.addAttribute("user", user);
            model.addAttribute("categories", categories);
            return "users/user_edit";
        } catch (NotFoundException e) {
            ra.addFlashAttribute("error_message", "対象のデータが見つかりませんでした");
            return "redirect:/users/mypage";
        }
    }

    /**
     * 編集内容登録処理
     *
     * @param user ユーザー情報
     * @param ra
     * @return スレッド一覧画面
     */
    @PostMapping("/save2")
    public String save2User(User user, RedirectAttributes ra) {
    	//入力された文字数のチェック
        if (!userService.isValid(user.getEmail(), user.getName())) {
            ra.addFlashAttribute("error_message", "メールアドレスまたはユーザー名の文字数がオーバーしています");
            return "redirect:/users/user_edit";
        }

        //ユーザー情報のユーザー名重複チェック
        if (!userService.UserNamecheckUnique(user)) {
            ra.addFlashAttribute("error_message", "変更しようとしたユーザー名は既に使用されています。");
            return "redirect:/users/user_edit";
        }

        //ユーザー情報のメールアドレス重複チェック
        if (!userService.UserEmailcheckUnique(user)) {
            ra.addFlashAttribute("error_message", "変更しようとしたメールアドレスは既に使用されています。");
            return "redirect:/users/user_edit";
        }

        //ユーザー情報の登録
        userService.save2(user);
        // 登録成功のメッセージを格納
        ra.addFlashAttribute("success_message", "ユーザー情報の編集に成功しました");
        return "redirect:/users/mypage";
    }

}
