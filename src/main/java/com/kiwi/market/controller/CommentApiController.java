package com.kiwi.market.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kiwi.market.entity.Comment;
import com.kiwi.market.service.CommentService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class CommentApiController {

	private final CommentService commentService;
	// private final MemberService memberService;

	@ResponseBody  // ajax로 보낼 경우 사용
	@GetMapping("/market/comment/{marketId}")
	public void save(Long marketId, String content,	Comment comment) { // , , @AuthenticationPrincipal PrincipalDetails principalDetails- 로그인 한 유저 정보가져오기
		// Long memberId = memberService.getIdFromAuth(principalDetails);
		// System.out.println("==================>"+ memberId);
		commentService.commentSave(marketId, content, comment); // , principalDetail.getMember()
		// System.out.println(">>>>>>>>>>>>>>>>>>>>>>" + principalDetails.getMember());
	}

	// 댓글 삭제
	@ResponseBody
	@GetMapping("/market/commentDelete/{commentId}")
	public void commentDelete(Long commentId) {
		System.out.println(">>>>>>>>>>>>>컨트롤 접근 : " + commentId);
		commentService.deleteComment(commentId);
	}

}
