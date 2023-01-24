package com.example.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="USER_CATEGORIES")
public class UserCategories {

	@Id
	@SequenceGenerator(name = "USER_CATEGORY_ID_GENERATOR", sequenceName = "USER_CATEGORY_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USER_CATEGORY_ID_GENERATOR")
    @Column(name = "ID")
	private Long id;

	@Column(name="USER_ID")
	private Long userId;

	@Column(name="CATEGORY_ID")
	private Long categoryId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

}
