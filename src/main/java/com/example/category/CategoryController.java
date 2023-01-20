package com.example.category;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.entity.Categories;
import com.example.entity.Threads;
import com.example.thread.ThreadService;

@Controller
@RequestMapping("/categories")
public class CategoryController {

	private final CategoryService categoryService;
	private final ThreadService threadService;

	public CategoryController(CategoryService categoryService, ThreadService threadService) {
		this.categoryService = categoryService;
		this.threadService = threadService;
	}

	/**
	 * カテゴリ一覧表示
	 */
	@GetMapping
	public String showCategories(Model model) {
		List<Categories> categories = this.categoryService.listAll();
		model.addAttribute("categories", categories);
		return "view/category";
	}

	/**
	 * カテゴリスレッド検索スレッド表示
	 * @param categoryId
	 */
	@GetMapping("/threads/{categoryId}")
	public String showThreads(@PathVariable Long categoryId, Model model) {
		List<Threads> threads = this.threadService.findByCategory(categoryId);
		model.addAttribute("threads", threads);
		return "view/thredCategory";
	}


}
