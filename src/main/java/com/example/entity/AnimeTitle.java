package com.example.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "ANIME_TITLES")
public class AnimeTitle {

	@Id
	@SequenceGenerator(name = "ANIME_TITLES_ID_GENERATOR", sequenceName = "ANIME_TITLE_SEQ", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ANIME_TITLES_ID_GENERATOR")
	@Column(name = "ID")
	private Long id;

	@Column(name = "NAME")
	private String name;

	@Column(name = "CATEGORY_ID")
	private Long categoryId;

	@OneToMany(mappedBy = "animeTitle",  cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Threads> threadList;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public List<Threads> getThreadList() {
		return threadList;
	}

	public void setThreadList(List<Threads> threadList) {
		this.threadList = threadList;
	}


}
