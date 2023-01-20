package com.example.category;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entity.Categories;

public interface CategoryRepository extends JpaRepository<Categories, Long>{

}
