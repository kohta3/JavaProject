package com.example.user;

import java.util.ArrayList;
import java.util.HashSet;
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
import com.example.block.BlockService;
import com.example.category.CategoryService;
import com.example.entity.AnimeTitle;
import com.example.entity.Block;
import com.example.entity.Categories;
import com.example.entity.Follow;
import com.example.entity.User;
import com.example.entity.UserCategories;
import com.example.follow.FollowService;
import com.example.security.A2ChannelUserDetails;
import com.example.userCategories.UserCategoriesService;


@Controller
@RequestMapping("/users")
public class UserController extends Thread{

	@Override
	public void run() {

	}



    private final UserService userService;
    private final FollowService followService;
	private final CategoryService categoryService;
	private final AnimeTitleService animeTitleService;
	private final UserCategoriesService userCategoriesService;
	private final BlockService blockService;

    @Autowired
    public UserController(UserService userService,CategoryService categoryService, AnimeTitleService animeTitleService,UserCategoriesService userCategoriesService, FollowService followService, BlockService blockService) {
        this.userService = userService;
        this.categoryService = categoryService;
		this.animeTitleService = animeTitleService;
		this.userCategoriesService = userCategoriesService;
		this.followService = followService;
		this.blockService = blockService;
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
    //@Async
    @PostMapping("/save")
    public String saveUser(User user, RedirectAttributes ra) {

    	//入力されたメールアドレスの文字数チェック
        if (!userService.isValidEmail(user.getEmail())) {
            ra.addFlashAttribute("error_message", "メールアドレスは10文字以上254文字以内で入力してください");
            return "redirect:/users/new";
        }

    	//入力されたユーザー名の文字数チェック
        if (!userService.isValidName(user.getName())) {
            ra.addFlashAttribute("error_message", "ユーザー名は、1文字以上100文字以内で入力してください");
            return "redirect:/users/new";
        }

    	//入力された自己紹介の文字数チェック
        if (!userService.isValidIntroduction(user.getIntroduction())) {
            ra.addFlashAttribute("error_message", "自己紹介は、1文字以上300文字以内で入力してください");
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
        Long userReturnId=userService.save(user).getId();


       //category情報の保存
        if(user.getUserCategories() != null) {
        	for (UserCategories element : user.getUserCategories()) {
            	UserCategories userCategories = new UserCategories();
                userCategories.setCategoryId(element.getId());
                userCategories.setUserId(userReturnId);
                userCategoriesService.save(userCategories);
    		}
        }


        //登録成功のメッセージを格納
        ra.addFlashAttribute("success_message", "ユーザーの新規登録に成功しました");
        return "redirect:/";
    }

    @GetMapping("/recommend")
    private String RecommendUser(Model model,@AuthenticationPrincipal A2ChannelUserDetails loginUser) {

    	List<UserCategories> userCategories = this.userCategoriesService.findByUserId(loginUser.getUser().getId());
    	List<UserCategories> usersInfo = new ArrayList<UserCategories>();
    	List<Long> usersId = new ArrayList<Long>();

    	//ログインユーザーのもつお気に入りカテゴリーを取得
    	for (UserCategories userCategory : userCategories) {
    		usersInfo.addAll(this.userCategoriesService.findByCategoryId(userCategory.getCategoryId())) ;
		}

    	//共通のお気に入りジャンルを持つユーザーを取得
    	for (UserCategories userInfo : usersInfo) {
    		usersId.add(userInfo.getUserId());
		}

    	//重複するおすすめユーザーを削除
    	List<Long> uniqueUsersId = new ArrayList<Long>(new HashSet<>(usersId));

    	System.out.println(uniqueUsersId);

    	List<User> recommendUsers = new ArrayList<User>();
    	//htmlに渡すおすすめユーザーの取得
	    for (Long userNum : uniqueUsersId) {
	    	recommendUsers.add(this.userService.getByid(userNum));
		}

	    //htmlに渡すフォローリスト
	    List<Long> followUserList = this.followService.listUserId(loginUser.getUser().getId());

	    //htmlに渡すブロックリスト
	    List<Long> blockUserList = this.blockService.listUserId(loginUser.getUser().getId());

//	    for (Long long : followUserList) {
//	    	System.out.println(followUserList);
//		}
	    model.addAttribute("blocks", blockUserList);
	    model.addAttribute("follows", followUserList);
    	model.addAttribute("loginUser",loginUser.getUser().getName());
		model.addAttribute("recommendUser",recommendUsers);

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
    @GetMapping("/mypage/{userId}")
    public String MyPages(Model model,@PathVariable("userId") Long userId,@AuthenticationPrincipal A2ChannelUserDetails loginUser) {
    	try {
    		//ユーザー情報取得
			User user = this.userService.get(userId);
			//カテゴリー情報取得
			List<Categories> categories = this.categoryService.listAll();
			//フォロー情報取得
	    	List<Follow> followList = this.followService.listAll(user.getId());
	    	//ブロック情報取得
	    	List<Block> blockList = this.blockService.listAll(user.getId());

			//画面に情報を渡す
            model.addAttribute("categories", categories);
			model.addAttribute("follows", followList);
			model.addAttribute("blocks", blockList);
	    	model.addAttribute("user", user);
	    	return "users/mypage";
		} catch (NotFoundException e) {
			e.printStackTrace();
			return "";
		}
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
            System.out.println(categories);
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
    	//入力されたメールアドレスの文字数チェック
        if (!userService.isValidEmail(user.getEmail())) {
            ra.addFlashAttribute("error_message", "メールアドレスの文字数がオーバーしています");
            return "redirect:/users/user_edit";
        }

    	//入力されたユーザー名の文字数チェック
        if (!userService.isValidName(user.getName())) {
            ra.addFlashAttribute("error_message", "ユーザー名は、1文字以上100文字以内で入力してください");
            return "redirect:/users/user_edit";
        }

    	//入力されたユーザー名の文字数チェック
//       if (!userService.isValidIntroduction(user.getIntroduction())) {
//            ra.addFlashAttribute("error_message", "自己紹介は、1文字以上300文字以内で入力してください");
//            return "redirect:/users/user_edit";
//        }

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
        Long userReturnId=userService.save(user).getId();


       //category情報の保存
        if(user.getUserCategories() != null) {
        	for (UserCategories element : user.getUserCategories()) {
        		UserCategories userCategories = new UserCategories();
        		userCategories.setCategoryId(element.getId());
        		userCategories.setUserId(userReturnId);
        		userCategoriesService.save(userCategories);
        	}
        }

        //登録成功のメッセージを格納

 //       ra.addFlashAttribute("success_message", "ユーザー情報の編集に成功しました");
        return "redirect:/users/mypage/" + user.getId();
    }


}
