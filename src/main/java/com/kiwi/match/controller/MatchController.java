package com.kiwi.match.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;

@RequestMapping("/match")
@Controller
public class MatchController {

	// 매치 메인 - 매치 리스트
	@GetMapping("/matchList")
	public String matchList() {
		return "match/matchList";
	}
	
	// 매치 디테일
	@GetMapping("/matchDetail")
	public String matchDetail() {
		return "match/matchDetail";
	}
	
	

}
