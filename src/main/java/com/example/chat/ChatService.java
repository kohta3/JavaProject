package com.example.chat;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.entity.Chat;

@Service
public class ChatService {

    private final ChatRepository chatRepository;

	public ChatService(com.example.chat.ChatRepository chatRepository) {
		super();
		this.chatRepository = chatRepository;
	}

	public Chat save(Chat chat) {
		return chatRepository.save(chat);
	}

	//スレッド内表示メソッド
	public List<Chat> chatMatching(Long userId, Long followId) {
		List<Chat> matchingChat = this.chatRepository.findByUserIdAndFollowIdOrderByDateTimeAsc(userId,followId);
		return matchingChat;
	}

}
