package com.example.entity;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 *
 * スレッド情報
 *
 */
@Entity
@Table(name = "SLEDES")
public class Threads {

	@Id
	@SequenceGenerator(name = "THREADS_ID_GENERATOR", sequenceName = "SLEDES_SEQ", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "THREADS_ID_GENERATOR")
	@Column(name = "ID")
	private Long id;

    @NotBlank(message = "スレッドタイトルを入力してください")
    @Size(min=1, max = 50, message = "1文字以上50文字以内で入力してください")
	@Column(name = "TITLE")
	private String title;

    @NotBlank(message = "1コメ目を入力してください")
    @Size(min=1, max =600, message = "1文字以上600文字以内で入力してください")
	@Column(name = "COMMENTS")
	private String comment;

	@Column(name = "IMAGE")
	private String image;

	@Column(name = "DATE_TIME")
	private LocalDateTime dateTime;

	@Column(name = "USER_ID")
	private Long userId;

	@Column(name = "CATEGORY_ID")
	private Long categoryId;

	@Column(name = "ANIME_ID")
	private Long animeId;

	@Column(name = "COMMENT_SUM")
	private Long commentSum;

	@ManyToOne
	@JoinColumn(name = "USER_ID", insertable = false, updatable = false)
	private User user;

	@ManyToOne
	@JoinColumn(name = "CATEGORY_ID", insertable = false, updatable = false)
	private Categories category;

	@ManyToOne
	@JoinColumn(name = "ANIME_ID", insertable = false, updatable = false)
	private AnimeTitle animeTitle;

	@OneToMany(mappedBy = "threads", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Comment> commentList;

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Long getCommentSum() {
		return commentSum;
	}

	public void setCommentSum(Long commentSum) {
		this.commentSum = commentSum;
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

	public Long getAnimeId() {
		return animeId;
	}

	public void setAnimeId(Long animeId) {
		this.animeId = animeId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Categories getCategory() {
		return category;
	}

	public void setCategory(Categories category) {
		this.category = category;
	}

	public AnimeTitle getAnimeTitle() {
		return animeTitle;
	}

	public void setAnimeTitle(AnimeTitle animeTitle) {
		this.animeTitle = animeTitle;
	}

	public List<Comment> getCommentList() {
		return commentList;
	}

	public void setCommentList(List<Comment> commentList) {
		this.commentList = commentList;
	}



}
