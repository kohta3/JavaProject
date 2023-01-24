package com.example.user;

import java.util.List;

import com.example.entity.Categories;
import com.example.entity.User;

public class NewUserForm {

	private User user = new User();

	private List<Categories>  categories;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<Categories> getCategories() {
		return categories;
	}

	public void setCategories(List<Categories> categories) {
		this.categories = categories;
	}

}
