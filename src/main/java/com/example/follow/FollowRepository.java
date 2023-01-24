package com.example.follow;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.Follow;

@Repository
public interface FollowRepository extends JpaRepository<Follow, Long> {
	//ユーザーIDからフォロー情報のリストを取得
	public List<Follow> findByUserId(Long userId);
}
