package com.example.category;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Categories;

@Service
public class CategoryService {

	private final CategoryRepository categoryRepository;

	@Autowired
	public CategoryService(CategoryRepository categoryRepository) {
		super();
		this.categoryRepository = categoryRepository;
	}

	//カテゴリ一覧を返す
	public List<Categories> listAll() {
		return this.categoryRepository.findAll();
	}

	//カテゴリIDからカテゴリ情報を検索
	public Categories findById(Long id) {
		return this.categoryRepository.findById(id).get();
	}
}
