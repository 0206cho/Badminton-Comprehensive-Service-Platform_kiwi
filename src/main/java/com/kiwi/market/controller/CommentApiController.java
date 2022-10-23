package com.kiwi.market.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.kiwi.config.auth.PrincipalDetails;
import com.kiwi.market.entity.Comment;
import com.kiwi.market.service.CommnetService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class CommentApiController {

	private final CommnetService commentService;

//	@PostMapping("/api/v1/market/{marketId}/comment")
//	public void save(@PathVariable Long marketId, @RequestBody Comment comment) { // ,	@AuthenticationPrincipal PrincipalDetail principalDetail
//		System.out.println(">>>>>>>>>>>>>>>>>>>>>> 출력");
//		commentService.commentSave(marketId, comment); //, principalDetail.getMember()
//	}
	
	@GetMapping("/market/comment/{marketId}")
	public void save(Long marketId, String content, Comment comment) { // ,	, @AuthenticationPrincipal PrincipalDetails principalDetails- 로그인 한 유저 정보 가져오기
		System.out.println(">>>>>>>>>>>>>>>>>>>>>> save 들어옴");
		commentService.commentSave(marketId, content ,comment); //, principalDetail.getMember()
	}

}
