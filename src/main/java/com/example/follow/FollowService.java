package com.example.follow;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.entity.Follow;

@Service
public class FollowService {

	private final FollowRepository followRepository;

	public FollowService(FollowRepository followRepository) {
		this.followRepository = followRepository;
	}

	/**
	 * フォローしているユーザー情報取得
	 */
	public List<Follow> listAll(Long userId) {
		List<Follow> followList = this.followRepository.findByUserId(userId);
		return followList;
	}

	/*
	 * フォローユーザー登録
	 */
	public void save(Follow follow) {
		//フォローユーザーとフォロワーユーザーが同じ場合登録しない
		if(isFollowFollowerSame(follow)) {
			return;
		}
		//フォロー情報がある場合登録しない
		if(isExist(follow)) {
			return;
		}
		//フォロー情報がない場合登録する
		this.followRepository.save(follow);
	}


	//フォロー情報があるかチェック
	public boolean isExist(Follow follow) {
		//１，フォローしたいユーザーのID
		Long wantFollowId = follow.getFollowId();
		//２，フォローするユーザーのID
		Long userId = follow.getUserId();
		//２からユーザーIDのフォローリスト情報のリスト取得
		List<Follow> listFollow = this.followRepository.findByUserId(userId);
		//フォロー情報があるかの判定
		for(Follow element : listFollow) {
			if(element.getFollowId() == wantFollowId) {
				return true;
			}
		}
		return false;

	}

	//フォローリストからフォロー情報があるかチェック
	public boolean isFollowExist(Long userId, Long wantId) {
		List<Follow> follows = this.listAll(userId);
		for(Follow follow : follows) {
			if(follow.getFollowId() == wantId) {
				return true;
			}
		}
		return false;
	}

	//フォローユーザーとフォロワーユーザーが同じかチェック
	private boolean isFollowFollowerSame(Follow follow) {
		if(follow.getUserId() == follow.getFollowId()) {
			return true;
		}
		return false;
	}


}
