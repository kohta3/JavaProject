package com.example.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "COMMENTS")
public class Comment {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
	private Long id;

    @NotBlank(message = "コメントを入力してください")
    @Size(min=1, max = 150, message = "コメントは150文字以内で入力してください")
	@Column(name = "CONTENT")
	private String content;

	@Column(name = "IMAGE")
	private String image;

	@Column(name = "DATE_TIME")
	private LocalDateTime dateTime;

	@Column(name = "USER_ID")
	private Long userId;

	@Column(name = "THREAD_ID")
	private Long threadId;

	@ManyToOne
	@JoinColumn(name = "USER_ID", insertable = false, updatable = false)
	private User user;

	@ManyToOne
	@JoinColumn(name = "THREAD_ID", insertable = false, updatable = false)
	private Threads threads;

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

	public LocalDateTime getDateTime() {
		return dateTime;
	}

	public void setDateTime(LocalDateTime dateTime) {
		this.dateTime = dateTime;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getThreadId() {
		return threadId;
	}

	public void setThreadId(Long threadId) {
		this.threadId = threadId;
	}

	public Threads getThreads() {
		return threads;
	}

	public void setThreads(Threads thread) {
		this.threads = thread;
	}

}
