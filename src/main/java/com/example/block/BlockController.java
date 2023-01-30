package com.example.block;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.entity.Block;
import com.example.entity.Follow;
import com.example.follow.FollowService;
import com.example.security.A2ChannelUserDetails;

@Controller
@RequestMapping("/blocks")
public class BlockController {

	private final BlockService blockService;
	private final FollowService followService;

	public BlockController(FollowService followService, BlockService blockService) {
		super();
		this.blockService = blockService;
		this.followService = followService;
	}

	/**
	 * ブロックユーザー登録
	 * @Param loginUser
	 * @param blockId
	 * @return ブロック前の画面
	 */
	@PostMapping("/save")
	public String createBlock(@RequestParam("blockId") Long blockId, RedirectAttributes ra ,@RequestParam("url") String url, @AuthenticationPrincipal A2ChannelUserDetails loginUser) {
		Follow follow = this.followService.getByUserIdAndFollowId(loginUser.getUser().getId(), blockId);
		if(follow == null) {
			Block block = new Block();
			block.setUserId(loginUser.getUser().getId());
			block.setBlockId(blockId);
			this.blockService.save(block);
		} else {
			ra.addFlashAttribute("error_message", "ブロックする場合は、先にフォローを外してください。");
		}

		return "redirect:" + url;
	}

	/**
	 * ブロック情報削除
	 */
	@PostMapping("/delete")
	public String deleteBlock(@RequestParam("blockId") Long blockId, @RequestParam("url") String url, @AuthenticationPrincipal A2ChannelUserDetails loginUser) {
		Block block = this.blockService.getByUserIdAndBlockId(loginUser.getUser().getId(), blockId);
		this.blockService.deleteBlock(block);
		return "redirect:" + url;
	}

}
