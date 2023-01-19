package com.example.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="COMMENTS")
public class Comment {

	@Column(name="ID")
	private Long id;

	@Column(name="CONTENT")
	private String content;

	@Column(name="IMAGE")
	private String image;

	@Column(name="DATE_TIME")
	private LocalDateTime date_time;

	@ManyToOne
	@JoinColumn(insertable = false, updatable = false)
	private User user;

	@ManyToOne
	@JoinColumn(insertable = false, updatable = false)
	private THREAD thread;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public LocalDateTime getDate_time() {
		return date_time;
	}

	public void setDate_time(LocalDateTime date_time) {
		this.date_time = date_time;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public THREAD getThread() {
		return thread;
	}

	public void setThread(THREAD thread) {
		this.thread = thread;
	}

}
