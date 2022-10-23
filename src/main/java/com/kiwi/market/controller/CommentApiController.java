package com.kiwi.market.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.kiwi.config.auth.PrincipalDetails;
import com.kiwi.market.entity.Comment;
import com.kiwi.market.service.CommentService;
import com.kiwi.member.entity.Member;
import com.kiwi.member.service.MemberService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class CommentApiController {

	private final CommentService commentService;
	//private final MemberService memberService;


	@GetMapping("/market/comment/{marketId}")
	public void save(@AuthenticationPrincipal PrincipalDetails principalDetails, Long marketId, String content, Comment comment) { // ,	, @AuthenticationPrincipal PrincipalDetails principalDetails- 로그인 한 유저 정보 가져오기
		//Long memberId = memberService.getIdFromAuth(principalDetails); 
		//System.out.println("==================>"+ memberId);
		commentService.commentSave(marketId, content ,comment); //, principalDetail.getMember()
		//System.out.println(">>>>>>>>>>>>>>>>>>>>>>" + principalDetails.getMember());
	}

}
