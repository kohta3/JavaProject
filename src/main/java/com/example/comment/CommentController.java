package com.example.comment;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.entity.Comment;

@Controller
@RequestMapping("/comment")
public class CommentController {

	private CommentService commentService;

	@Autowired
	public CommentController(CommentService commentService) {
		this.commentService = commentService;
	}

	@PostMapping("/create")
	public String createComment(Comment comment) {

		comment.setDateTime(LocalDateTime.now());
		comment.setUserId(1L);

		this.commentService.save(comment);
		return "redirect:/threads";
	}
	@GetMapping("/delete/{id}")
	public String detailCategory(@PathVariable(name = "id") Long id, Model model) {
		this.commentService.delete(id);
		return "redirect:/threads";
	}
}
