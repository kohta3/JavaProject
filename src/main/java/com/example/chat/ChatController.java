package com.example.chat;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.entity.Follow;
import com.example.follow.FollowService;
import com.example.security.A2ChannelUserDetails;
import com.example.user.UserService;

@Controller
@RequestMapping("/chats")
public class ChatController {

	private final ChatService chatService;
    private final UserService userService;
    private final FollowService followService;


    @Autowired
    public ChatController(ChatService chatService, UserService userService, FollowService followService) {
    	this.chatService = chatService;
    	this.userService = userService;
		this.followService = followService;
    }


	//チャット画面のホーム
	@GetMapping
	public String toppages(Model model,  @AuthenticationPrincipal A2ChannelUserDetails loginUser) {
		//フォロー情報取得
    	List<Follow> followList = this.followService.listAll(loginUser.getUser().getId());
		model.addAttribute("follows", followList);
		return "chats/home";
	}


	//チャット画面のホーム
	@GetMapping("/tokeroom")
	public String tokeroom() {
		return "chats/tokeroom";
	}

}
