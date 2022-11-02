package com.kiwi.match.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import com.kiwi.config.auth.PrincipalDetails;
import com.kiwi.market.dto.MarketDto;
import com.kiwi.market.entity.Market;
import com.kiwi.market.service.CommentService;
import com.kiwi.market.service.MarketService;
import com.kiwi.match.dto.MatchDto;
import com.kiwi.match.entity.Matchs;
import com.kiwi.match.service.MatchService;

import lombok.RequiredArgsConstructor;

@RequestMapping("/match")
@Controller
@RequiredArgsConstructor
public class MatchController {

	private final MatchService matchService;

	// 매치 메인 - 매치 리스트
	@GetMapping("/matchList")
	public String matchList(Model model) {
		//String courtName = matchService.courtTest(1L);
		
		List<Matchs> list =  matchService.courtTest();
		model.addAttribute("list", list);
		//model.addAttribute("courtName", courtName);
		
		return "match/matchList";
	}

	// 매치 디테일
	@GetMapping("/matchDetail")
	public String matchDetail() {
		return "match/matchDetail";
	}

	// 매치 개설하기
	@GetMapping("/matchNew")
	public String matchNew(Model model) {
		model.addAttribute("matchDto", new MatchDto());
		return "match/matchForm";
	}

//	@PostMapping(value = "/matchNew")
//	public String matchNew(@Valid MatchDto matchDto, Model model) {
//		try {
//			Match match = Match.createMatch(matchDto);
//			
//			matchService.saveMatch(match);
//		} catch (Exception e) {
//			model.addAttribute("errorMessage", "매치 중 에러가 발생하였습니다.");
//			return "market/marketForm";
//		}
//		// 상품이 정상적으로 등록되었다면 메인 페이지로 이동
//		return "redirect:/market/marketList";
//	}

}
