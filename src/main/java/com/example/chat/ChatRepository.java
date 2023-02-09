package com.example.chat;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.Chat;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Long> {

	List<Chat> findByUserIdAndFollowIdOrderByDateTimeAsc(Long userId, Long followId);

}
