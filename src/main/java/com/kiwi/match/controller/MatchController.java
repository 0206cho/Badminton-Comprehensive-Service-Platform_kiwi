package com.kiwi.match.controller;

import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.kiwi.config.auth.PrincipalDetails;
import com.kiwi.court.entity.Reservation;
import com.kiwi.court.repository.ReservationRepository;
import com.kiwi.market.dto.MarketDto;
import com.kiwi.market.entity.Market;
import com.kiwi.market.repository.MarketRepository;
import com.kiwi.market.service.CommentService;
import com.kiwi.market.service.MarketService;
import com.kiwi.match.constant.Level;
import com.kiwi.match.constant.Status;
import com.kiwi.match.dto.MatchDto;
import com.kiwi.match.dto.MatchsReservationDto;
import com.kiwi.match.entity.Matchs;
import com.kiwi.match.entity.MatchsReservation;
import com.kiwi.match.repository.MatchRepository;
import com.kiwi.match.service.MatchService;
import com.kiwi.match.service.MatchsReservationService;

import lombok.RequiredArgsConstructor;

@RequestMapping("/match")
@Controller
@RequiredArgsConstructor
public class MatchController {

	private final MatchService matchService;

	@Autowired
	private MatchRepository matchRepository;

	@Autowired
	private ReservationRepository reservationRepository;

	@Autowired
	private MatchsReservationService mrService;

	private Long id;

	// 매치 메인 - 매치 리스트
//	@GetMapping("/matchList")
//	public String matchList(Model model) {
//		//String courtName = matchService.courtTest(1L);
//		
//		List<Matchs> list =  matchService.matchList();
//		model.addAttribute("list", list);
//		
//		return "match/matchList";
//	}

	// 매치 메인 - 매치 리스트 (페이징)
	@GetMapping("/matchList")
	public String matchList(Model model,
			@PageableDefault(size = 5, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
		// String courtName = matchService.courtTest(1L);
		Page<Matchs> list = matchRepository.findAll(pageable);
		int startPage = Math.max(1, list.getPageable().getPageNumber() - 4);
		int endPage = Math.min(list.getTotalPages(), list.getPageable().getPageNumber() + 4);
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);
		model.addAttribute("list", list);

		return "match/matchList";
	}

	// 매치 디테일
	@GetMapping("/matchDetail/{id}")
	public String matchDetail(@PathVariable("id") Long id, Model model) {
		Matchs matchs = matchService.matchDetail(id);
		model.addAttribute("matchs", matchs);
		return "match/matchDetail";
	}

	// 매치 신청하기
	@ResponseBody // ajax로 보낼 경우 사용
	@GetMapping("/matchsReservation")
	public void mrSave(@Valid MatchsReservationDto mrDto, Long mathcshId, @AuthenticationPrincipal PrincipalDetails principalDetails) {
		// 매치 신청 하기
		Long memberId = principalDetails.getMember().getId();
		MatchsReservation mr = MatchsReservation.createMR(mrDto);
		matchService.saveMatchsReservation(mr, mathcshId, memberId);

		// 매치 신청 마감 확인
		List<MatchsReservation> list = mrService.mrCourt();

		int count = 0; 
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getMathshId().getId() == mathcshId) { // 매치 ID가 DB에 있는 경우 -> 매치를 신청한 경우
				count += 1; // 매치 신청 건 수 카운트
			}
		}

		if (mr.getMathshId().getCount() <= count) {
			mr.getMathshId().setStatus(Status.마감);

			Reservation reservation = mr.getMathshId().getReservation();
			Matchs match = mr.getMathshId();
			matchService.saveMatch(match, memberId, reservation);
			
		}
	}

	// 매치 개설하기
//	@GetMapping("/matchNew")
//	public String matchNew(Model model) {
//		model.addAttribute("matchDto", new MatchDto());
//		return "match/matchForm";
//	}

	// 매치 개설하기
	@GetMapping("/matchNew")
	public String matchNew(@Valid MatchDto matchDto, Model model,
			@AuthenticationPrincipal PrincipalDetails principalDetails) {
		Long memberId = principalDetails.getMember().getId();
		List<Reservation> list = matchService.matchsCourt();
		model.addAttribute("list", list);
		model.addAttribute("memberId", memberId);

		int count = 0;
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getMember().getId() == memberId) { // 예약 했을 경우
				count += 1; // 예약한 건 수 : count
			}
		}
		model.addAttribute("count", count);

		return "match/matchForm";
	}

	// 매치 개설
	@PostMapping("/matchNew")
	public String matchNewPost(@Valid MatchDto matchDto, Model model,
			@AuthenticationPrincipal PrincipalDetails principalDetails) {
		Long memberId = principalDetails.getMember().getId();
		Matchs match = Matchs.createMatch(matchDto);
		Long reservationId = (long) 5;

		Reservation reservation = reservationRepository.findById(reservationId).orElseThrow();
		System.out.println(">>>>>>>>>>>>>>> reservation : " + reservation);
		System.out.println(">>>>>>>>>>>>>>> match : " + match);
		matchService.saveMatch(match, memberId, reservation);
		return "match/matchForm";
	}

}
