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
@Table(name = "USERS")
public class User {

    @Id
    @SequenceGenerator(name = "USERS_ID_GENERATOR", sequenceName = "USERS_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USERS_ID_GENERATOR")
    @Column(name = "ID")
    private Long id;

    //メールアドレス
    @Column(name = "EMAIL", length = 50, nullable = false, unique = true)
    private String email;

    //ユーザー名
    @Column(name = "NAME", length = 100, nullable = false, unique = true )
    private String name;

    //パスワード
    @Column(name = "PASSWORD", length = 64, nullable = false)
    private String password;

    //自己紹介文
    @Column(name = "INTRODUCTION", length = 300, nullable = true)
    private String introduction;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Threads> threadList;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<UserCategories> userCategories;


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
