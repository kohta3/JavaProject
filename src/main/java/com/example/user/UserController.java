package com.example.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.animeTitle.AnimeTitleService;
import com.example.category.CategoryService;
import com.example.entity.AnimeTitle;
import com.example.entity.Categories;
import com.example.entity.User;
import com.example.entity.UserCategories;
import com.example.security.A2ChannelUserDetails;
import com.example.userCategories.UserCategoriesService;


@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
	private final CategoryService categoryService;
	private final AnimeTitleService animeTitleService;
	private final UserCategoriesService userCategoriesService;

    @Autowired
    public UserController(UserService userService,CategoryService categoryService, AnimeTitleService animeTitleService,UserCategoriesService userCategoriesService) {
        this.userService = userService;
        this.categoryService = categoryService;
		this.animeTitleService = animeTitleService;
		this.userCategoriesService = userCategoriesService;
    }

    /**
     * ユーザー新規登録画面表示
     *
     * @param model
     * @return ユーザー新規登録画面
     */
    @GetMapping("/new")
    public String newUser(Model model) {
        // 新規登録用に、空のユーザー情報作成
        User user = new User();
        model.addAttribute("user", user);
        return "users/user_form";
    }

    /**
     * ユーザー登録・更新処理
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

    	model.addAttribute("loginUser", loginUser.getUser());
    	return "users/mypage";
    }

}
