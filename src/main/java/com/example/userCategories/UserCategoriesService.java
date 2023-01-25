package com.example.userCategories;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.UserCategories;


@Service
public class UserCategoriesService {
    private final UserCategoriesRepository userCategoriesRepository;

    @Autowired
    public UserCategoriesService(UserCategoriesRepository userCategoriesRepository) {
    	this.userCategoriesRepository = userCategoriesRepository;
    }

    public List<UserCategories> findByUserId(Long id) {
    	 List<UserCategories> userCategories= userCategoriesRepository.findByUserId(id);
		return userCategories;
    }

    public List<UserCategories> findByCategoryId(Long id) {
   	 List<UserCategories> userCategories= userCategoriesRepository.findByCategoryId(id);
		return userCategories;
   }

    public UserCategories save(UserCategories userCategories) {
    	
    	return userCategoriesRepository.save(userCategories);
    }
}
