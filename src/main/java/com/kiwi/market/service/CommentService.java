package com.kiwi.market.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kiwi.market.entity.Comment;
import com.kiwi.market.entity.Market;
import com.kiwi.market.repository.CommentRepository;
import com.kiwi.market.repository.MarketRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CommentService {

	private final CommentRepository commentRepository;
	private final MarketRepository marketRepository;

	@Transactional
	public void commentSave(Long marketId, String content, Comment comment) { // Member member
		Market market = marketRepository.findById(marketId)
				.orElseThrow(() -> new IllegalArgumentException("해당 marketId가 없습니다. id=" + marketId));

		comment.save(market, content); // , member

		commentRepository.save(comment);
	}

	public List<Comment> commentList() {
		List<Comment> list = commentRepository.findAllByOrderByIdDesc();
		return list;
	}

}
