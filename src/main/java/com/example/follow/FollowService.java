package com.example.follow;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.entity.Follow;
import com.example.entity.User;

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

	/**
	 * フォローしているユーザーのIDのリストを取得
	 * @param follow
	 */
	public List<Long> listUserId(Long userId) {
		List<Follow> followList = listAll(userId);
		List<Long> listNum = new ArrayList<Long>();
		for(Follow follow : followList) {
			listNum.add(follow.getFollowId());
		}
		return listNum;
	}

	/**
	 * フォローワーさんのユーザーIDのリストを取得
	 * @param follow
	 */
	public List<Long> passiveFollowUserId(Long userId) {
		List<Follow> followList = this.followRepository.findByFollowId(userId);
		List<Long> listNum = new ArrayList<Long>();
		for(Follow follow : followList) {
			listNum.add(follow.getUserId());
		}
		return listNum;
	}

	/**
	 * フォローワーさんのユーザー情報のリストを取得
	 * @param follow
	 */
	public List<User> followers(Long userId) {
		List<Follow> followList = this.followRepository.findByFollowId(userId);
		List<User> followUsers = new ArrayList<>();
		for(Follow follow : followList) {
			followUsers.add(follow.getUser());
		}
		return followUsers;
	}

	/**
	 * フォローワーさんでまだフォロバしてない人のユーザー情報のリストを取得
	 * @param follow
	 */
	public List<User> followBackwait(Long userId) {
		List<User> followersNotFollow = new ArrayList<>();
		List<Follow> followList = this.followRepository.findByFollowId(userId);
		for(Follow follow : followList) {
			if(!this.isFollowExist(userId, follow.getUserId())) {
				followersNotFollow.add(follow.getUser());
			}
		}
		return followersNotFollow;
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

	/**
	 * フォローユーザーIDとユーザーIDからフォロー情報取得
	 * @param follow
	 * @return
	 */
	public Follow getByUserIdAndFollowId(Long userId, Long followId) {
		return this.followRepository.findByUserIdAndFollowId(userId, followId);
	}

	/**
	 * フォロー情報削除
	 * @param follow
	 * @return
	 */
	public void deleteFollow(Follow follow) {
		this.followRepository.delete(follow);
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
