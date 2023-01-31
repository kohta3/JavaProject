package com.example.follow;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.Follow;

@Repository
public interface FollowRepository extends JpaRepository<Follow, Long> {
	//ユーザーIDからフォロー情報のリストを取得
	public List<Follow> findByUserId(Long userId);
	//ユーザーIDとフォローIDからフォロー情報を取得
	public Follow findByUserIdAndFollowId(Long userId, Long followId);
	//フォローIDからフォロー情報を取得
	public List<Follow> findByFollowId(Long userId);
}
