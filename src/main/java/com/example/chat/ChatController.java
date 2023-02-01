package com.example.chat;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponentsBuilder;

import com.example.entity.Chat;
import com.example.entity.Follow;
import com.example.follow.FollowService;
import com.example.security.A2ChannelUserDetails;

@Controller
@RequestMapping("/chats")
public class ChatController {

	private final ChatService chatService;

    private final FollowService followService;


    @Autowired
    public ChatController(ChatService chatService, FollowService followService) {
    	this.chatService = chatService;
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

	//チャットルーム
	@GetMapping("/talkroom/{id}")
	public String tokeroom(@PathVariable(name = "id") Long id, Model model, @AuthenticationPrincipal A2ChannelUserDetails loginUser) {
		Chat chat = new Chat();
		List<Chat> chats =this.chatService.chatMatching(loginUser.getUser().getId(), id);
		System.out.println(chats);
		model.addAttribute("id", id);
		model.addAttribute("chat",chat);
		model.addAttribute("chats",chats);
		return "chats/talkroom";
	}

	@PostMapping("/create")
	public String createChat(Chat chat, @RequestParam(name = "followId", required = false) Long followId,  BindingResult result, RedirectAttributes ra, @AuthenticationPrincipal A2ChannelUserDetails loginUser, UriComponentsBuilder builder) {

		chat.setDateTime(LocalDateTime.now());
		chat.setUserId(loginUser.getUser().getId());
		chat.setFollowId(followId);
		if(result.hasErrors()) {
			//正しい値が入力されているか
			List<String> errorList = new ArrayList<String>();
			//エラーがある場合、リストに格納していく
			for(ObjectError error : result.getAllErrors()) {
				errorList.add(error.getDefaultMessage());
			}
			ra.addFlashAttribute("validationError", errorList);
			return "redirect:/chats/talkroom/" + followId;

		}

		this.chatService.save(chat);

		URI location = builder.path("/chats/talkroom/" + followId).build().toUri();

		return "redirect:"+ location.toString();
	}

}
