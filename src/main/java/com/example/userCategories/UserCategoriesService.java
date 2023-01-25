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

    	if(userCategories == null) {
    		return userCategories;
    	}

    	Long id = userCategories.getUserId();
    	Long wantId = userCategories.getCategoryId();



    	if(isUserCategories(id, wantId)) {
    		return userCategories;
    	}

    	return userCategoriesRepository.save(userCategories);
    }

    private boolean isUserCategories(Long id, Long wantId) {
    	List<UserCategories> userCategories = this.userCategoriesRepository.findByUserId(id);

    	for(UserCategories category : userCategories) {
    		if(category.getCategoryId() == wantId) {
    			return true;
    		}
    	}
    	return false;
    }
}
