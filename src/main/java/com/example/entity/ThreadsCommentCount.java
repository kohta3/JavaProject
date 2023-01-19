package com.example.entity;

import java.time.LocalDateTime;

public class ThreadsCommentCount {



	private Long id;
	private String title;
	private String comment;
	private LocalDateTime dateTime;
	private String userName;
	private String categoryName;
	private String animeTitle;
	private Long commentCount;



	public ThreadsCommentCount(Long id, String title, String comment, LocalDateTime dateTime, String userName,
			String categoryName, String animeTitle, Long commentCount) {
		super();
		this.id = id;
		this.title = title;
		this.comment = comment;
		this.dateTime = dateTime;
		this.userName = userName;
		this.categoryName = categoryName;
		this.animeTitle = animeTitle;
		this.commentCount = commentCount;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public LocalDateTime getDateTime() {
		return dateTime;
	}
	public void setDateTime(LocalDateTime dateTime) {
		this.dateTime = dateTime;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public String getAnimeTitle() {
		return animeTitle;
	}
	public void setAnimeTitle(String animeTitle) {
		this.animeTitle = animeTitle;
	}
	public Long getCommentCount() {
		return commentCount;
	}
	public void setCommentCount(Long commentCount) {
		this.commentCount = commentCount;
	}


}
