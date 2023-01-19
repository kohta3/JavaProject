package com.example.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.entity.Role;
import com.example.entity.User;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * ユーザー新規登録画面表示
     *
     * @param model
     * @return ユーザー新規登録画面
     */
    @GetMapping("/new")
    public String newUser(Model model) {
        // 新規登録用に、空の管理者情報作成
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
        // 入力値のチェック
        if (!userService.isValid(user.getEmail(), user.getName())) {
            ra.addFlashAttribute("error_message", "入力に誤りがあります");
            return "redirect:/products/new";
        }

        //ユーザー情報の重複チェック
        if (!userService.checkUnique(user)) {
            ra.addFlashAttribute("error_message", "重複しています");
            return "redirect:/products/new";
        }

        //ユーザー情報の登録
        userService.save(user);
        // 登録成功のメッセージを格納
        ra.addFlashAttribute("success_message", "登録に成功しました");
        return "redirect:/threads";
    }

    /**
     * 管理者詳細画面表示
     *
     * @param id 管理者ID
     * @param model
     * @param ra
     * @return 管理者詳細画面
     */
    @GetMapping("/detail/{id}")
    public String detailUser(@PathVariable(name = "id") Long id, Model model, RedirectAttributes ra) {
        try {
            // 管理者IDに紐づく管理者情報取得
            User user = userService.get(id);
            model.addAttribute("user", user);
            return "users/user_detail";
        } catch (NotFoundException e) {
            ra.addFlashAttribute("error_message", "対象のデータが見つかりませんでした");
            return "redirect:/users";
        }
    }

    /**
     * 管理者編集画面表示
     *
     * @param id 管理者ID
     * @param model
     * @param ra
     * @return 管理者編集画面
     */
    @GetMapping("/edit/{id}")
    public String editUser(@PathVariable(name = "id") Long id, Model model, RedirectAttributes ra) {
        try {
            // 管理者IDに紐づく管理者情報取得
            User user = userService.get(id);
            model.addAttribute("user", user);
        } catch (NotFoundException e) {
            ra.addFlashAttribute("error_message", "対象のデータが見つかりませんでした");
            return "redirect:/users";
        }

        // 全ロール情報の取得
        List<Role> listRoles = userService.listRoles();
        model.addAttribute("listRoles", listRoles);
        return "users/user_edit";
    }


}
