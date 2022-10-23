package com.kiwi.market.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kiwi.market.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
	
}