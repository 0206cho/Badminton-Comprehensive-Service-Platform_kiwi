package com.kiwi.match.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kiwi.market.dto.MarketDto;

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
	
	// 매치 개설하기
	@GetMapping("/matchNew")
	public String matchNew() {
		return "match/matchForm";
	}
	

}
