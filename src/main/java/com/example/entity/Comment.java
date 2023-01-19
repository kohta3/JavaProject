package com.example.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "COMMENTS")
public class Comment {

	@Id
	@SequenceGenerator(name = "COMMENTS_ID_GENERATOR", sequenceName = "COMMENTS_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "COMMENTS_ID_GENERATOR")
    @Column(name = "ID")
	private Long id;

	@Column(name = "CONTENT", length = 150)
	private String content;

	@Column(name = "IMAGE" , nullable = true)
	private String image;

	@Column(name = "DATE_TIME")
	private LocalDateTime dateTime;

	@Column(name = "USER_ID")
	private Long userId;

	@Column(name = "THREAD_ID")
	private Long threadId;

	@ManyToOne
	@JoinColumn(name = "user_id", insertable = false, updatable = false)
	private User user;

	@ManyToOne
	@JoinColumn(name = "thread_id", insertable = false, updatable = false)
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

	public LocalDateTime getDate_time() {
		return dateTime;
	}

	public void setDate_time(LocalDateTime date_time) {
		this.dateTime = date_time;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Threads getThread() {
		return threads;
	}

	public void setThread(Threads thread) {
		this.threads = thread;
	}
}
