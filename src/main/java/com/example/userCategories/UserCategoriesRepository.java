package com.example.userCategories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.UserCategories;

@Repository
public interface UserCategoriesRepository extends JpaRepository<UserCategories, Long> {
	public List<UserCategories> findByUserId(Long UserId);
	public List<UserCategories> findByCategoryId(Long CategoryId);
}
