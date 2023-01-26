package com.example.comment;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long>{
//	@Query("select c from COMMENTS c where c.thread_id = ?1")
//	List<Comment> myFindByThreadId(Long thread_id);
	public List<Comment> findBythreadIdOrderByDateTimeAsc(Long ThreadId);
}
