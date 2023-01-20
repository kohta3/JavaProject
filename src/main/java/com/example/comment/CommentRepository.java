package com.example.comment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long>{
}
