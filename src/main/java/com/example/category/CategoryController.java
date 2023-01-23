package com.example.category;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.animeTitle.AnimeTitleService;
import com.example.entity.AnimeTitle;
import com.example.entity.Categories;

@Controller
@RequestMapping("/categories")
public class CategoryController {

	private final CategoryService categoryService;
	private final AnimeTitleService animeTitleService;

	public CategoryController(CategoryService categoryService, AnimeTitleService animeTitleService) {
		this.categoryService = categoryService;
		this.animeTitleService = animeTitleService;
	}

	/**
	 * カテゴリ,アニメタイトル一覧表示
	 */
	@GetMapping
	public String showCategories(Model model) {
		List<Categories> categories = this.categoryService.listAll();
		List<AnimeTitle> animeTitles = this.animeTitleService.listAll();
		model.addAttribute("animeTitles", animeTitles);
		model.addAttribute("categories", categories);
		return "view/category";
	}

	/**
	 * カテゴリスレッド検索スレッド表示
	 * @param categoryId
	 */



}
