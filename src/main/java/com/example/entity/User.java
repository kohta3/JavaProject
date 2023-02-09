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
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


@Entity
@Table(name = "USERS")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @NotBlank(message = "メールアドレスを入力してください")
    @Size(max = 254, message = "メールアドレスは254文字以内で入力してください")
    @Column(name = "EMAIL", length = 254, nullable = false, unique = true)
    private String email;//メールアドレス

    @NotBlank(message = "ユーザー名を入力してください")
    @Size(max = 100, message = "ユーザー名は100文字以内で入力してください")
    @Column(name = "NAME", length = 100, nullable = false, unique = true )
    private String name;//ユーザー名

    @NotBlank(message = "パスワードを入力してください")
    @Size(min=4, max = 64, message = "パスワードは4文字以上64文字以内で入力してください")
    @Column(name = "PASSWORD", length = 64, nullable = false)
    private String password;//パスワード

    @NotBlank(message = "自己紹介を入力してください")
    @Size(max = 300, message = "自己紹介は300文字以内で入力してください")
    @Column(name = "INTRODUCTION", length = 300, nullable = true)
    private String introduction;//自己紹介文

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Threads> threadList;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Comment> commentList;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<UserCategories> userCategories;


	public List<Comment> getCommentList() {
		return commentList;
	}

	public void setCommentList(List<Comment> commentList) {
		this.commentList = commentList;
	}

	public List<UserCategories> getUserCategories() {
		return userCategories;
	}

	public void setUserCategories(List<UserCategories> userCategories) {
		this.userCategories = userCategories;

	}


	public List<Threads> getThreadList() {
		return threadList;
	}

	public void setThreadList(List<Threads> threadList) {
		this.threadList = threadList;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

}
