package com.example.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.category.CategoryService;
import com.example.entity.Categories;
import com.example.entity.User;
import com.example.security.A2ChannelUserDetails;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final CategoryService categoryService;

    @Autowired
    public UserController(UserService userService, CategoryService categoryService) {
        this.userService = userService;
        this.categoryService = categoryService;
    }

    /**
     * ユーザー新規登録画面表示
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
     * ユーザー新規登録処理
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

    /**
     * ユーザー情報編集画面
     *
     * @param user ユーザー情報
     * @param ra
     * @return 編集画面
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable(name = "id") Long id, Model model, RedirectAttributes ra) {
        try {
            // ユーザーIDに紐づくユーザー情報取得
            User user = userService.get(id);
            model.addAttribute("user", user);
            return "users/user_detail";
        } catch (NotFoundException e) {
            ra.addFlashAttribute("error_message", "対象のデータが見つかりませんでした");
            return "redirect:/users/mypage";
        }
    }

    /**
     * ユーザー情報登録処理
     *
     * @param user ユーザー情報
     * @param ra
     * @return マイページ画面
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
            ra.addFlashAttribute("error_message", "既に使用されているユーザー名です。");
            return "redirect:/users/user_edit";
        }

        //ユーザー情報のメールアドレス重複チェック
        if (!userService.UserEmailcheckUnique(user)) {
            ra.addFlashAttribute("error_message", "既に使用されているメールアドレスです");
            return "redirect:/users/user_edit";
        }

        //ユーザー情報の登録
        userService.save(user);
        // 登録成功のメッセージを格納
        ra.addFlashAttribute("success_message", "ユーザー情報の更新に成功しました");
        return "redirect:/users/mypage";
    }

}
