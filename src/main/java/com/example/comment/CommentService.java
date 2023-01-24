package com.example.comment;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Comment;

@Service
public class CommentService {
	private final CommentRepository commentRepository;

	@Autowired
	public CommentService(CommentRepository commentRepository) {
		this.commentRepository= commentRepository;
	}

	//全コメント表示
	public List<Comment> findAll() {
		List<Comment> allComment = this.commentRepository.findAll();
		return allComment;
	}

	//スレッド内表示メソッド
	public List<Comment> commentMatchingTheThread(Long threadId) {
		List<Comment> matchingComment = this.commentRepository.findBythreadId(threadId);
		return matchingComment;
	}

	public Comment save(Comment comment) {
		return commentRepository.save(comment);
	}

	public void delete(Long id) {
		 commentRepository.deleteById(id);
	}


}
