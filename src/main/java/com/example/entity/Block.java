package com.example.entity;

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
@Table(name = "BLOCKS")
public class Block {

	@Id
	@SequenceGenerator(name = "BLOCKS_ID_GENERATOR", sequenceName = "BLOCK_SEQ", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BLOCKS_ID_GENERATOR")
	@Column(name = "ID")
	private Long id;

	@Column(name = "USER_ID")
	private Long userId;

	@Column(name = "BLOCK_ID")
	private Long blockId;

	@ManyToOne
	@JoinColumn(name = "USER_ID", insertable = false, updatable = false)
	private User user;

	@ManyToOne
	@JoinColumn(name = "BLOCK_ID", insertable = false, updatable = false)
	private User blockUser;

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

	public Long getBlockId() {
		return blockId;
	}

	public void setBlockId(Long blockId) {
		this.blockId = blockId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public User getBlockUser() {
		return blockUser;
	}

	public void setBlockUser(User blockUser) {
		this.blockUser = blockUser;
	}


}
