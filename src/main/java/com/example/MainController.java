package com.example;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.example.animeTitle.AnimeTitleService;
import com.example.category.CategoryService;
import com.example.entity.AnimeTitle;
import com.example.entity.Categories;

@Controller
public class MainController {

	//共通部分に送る情報-----------------------
	private final CategoryService categoryService;
	private final AnimeTitleService animeTitleService;

	public MainController(CategoryService categoryService, AnimeTitleService animeTitleService) {
		this.animeTitleService = animeTitleService;
		this.categoryService = categoryService;
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
     * ログイン画面表示
     */
    @GetMapping("/loginForm")
    public String viewLoginPage() {
        return "loginForm";
    }

    /**
     * ログイン後の画面表示
     */
    @GetMapping("/thread")
    public String viewPage() {
        return "view/toppage";
    }

}
