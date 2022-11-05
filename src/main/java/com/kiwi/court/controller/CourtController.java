package com.kiwi.court.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kiwi.config.auth.PrincipalDetails;
import com.kiwi.court.dto.CourtDto;
import com.kiwi.court.dto.ReservationDto;
import com.kiwi.court.entity.Court;
import com.kiwi.court.entity.Reservation;
import com.kiwi.court.service.CourtService;
import com.kiwi.court.service.ReservationService;
import com.kiwi.member.entity.Member;
import com.kiwi.member.repository.MemberRepository;

@RequestMapping("/court")
@Controller
public class CourtController {

	@Autowired
	private CourtService courtService;
	
	@Autowired
	private ReservationService reservationService;
	
	@Autowired
	private MemberRepository memberRepository;

	// 코트 예약 페이지
	@GetMapping(value = "/reservation")
	public String courtReservation(Model model) {
		List<Court> courtAll = courtService.getCourtAll();
		model.addAttribute("courtList", courtAll);
		model.addAttribute("ReservationDto", new ReservationDto());
		
		List<Reservation> reserAll = reservationService.reserAll();
		model.addAttribute("reserList", reserAll);
		
		return "court/courtReservation";
	}

	// 코트 상세 페이지
	@GetMapping("/info/{courtId}")
	public String courtDtl(Model model, @PathVariable("courtId") Long courtId) {
		CourtDto courtDto = courtService.getCourtDtl(courtId);
		model.addAttribute("court", courtDto);
		return "court/courtInfo";
	}

	// 코트 예약 결제
	@GetMapping("/pay")
	public String payCourt(ReservationDto reservationDto, Model model) {
//		System.out.println("==================> dto : " + reservationDto);
//		System.out.println("==================> id : " + reservationDto.getCourt_id());
//		System.out.println("==================> name : " + reservationDto.getCourt_name());
//		System.out.println("==================> time : " + reservationDto.getCourt_time());
		return "pay/courtpay";
	}
	 
	// 코트 예약 결제
	@PostMapping("/pay")
	public String CourtResistry(ReservationDto reservationDto, Model model, @AuthenticationPrincipal PrincipalDetails principalDetails ) {
		
		System.out.println("==================> id : " + reservationDto.getCourt_id());
		System.out.println("==================> name : " + reservationDto.getCourt_name());
		System.out.println("==================> time : " + reservationDto.getCourt_time());
		System.out.println("==================> shuttlecock : " + reservationDto.getShuttlecock());
		System.out.println("==================> racket : " + reservationDto.getRacket());
		System.out.println("==================> btnNum : " + reservationDto.getBtnNum());
		
		Long id = principalDetails.getMember().getId();
		Member member = memberRepository.findMemberById(id);
		int money = member.getKiwicash();
		System.out.println("==================>" + money);
		model.addAttribute("money", money);
		
		
		reservationDto.subReserInfo(reservationDto, reservationDto.getReservation_info(),principalDetails.getUsername());
		model.addAttribute("reservationDto", reservationDto);
		
		
		
		return "pay/courtpay";
	}
	
	// 코트 예약 완료
	@PostMapping("/pay/result")
	@ResponseBody
	public void payResult(String id, String name, String num, String time, String racket, String email, String shuttlecock,String btnNum, @AuthenticationPrincipal PrincipalDetails principalDetails) {
		System.out.println( "=============> ajax에서 보낸 id : "+id);
		System.out.println( "=============> ajax에서 보낸 name : "+name);
		System.out.println( "=============> ajax에서 보낸 time : "+time);
		System.out.println( "=============> ajax에서 보낸 racket : "+racket);
		System.out.println( "=============> ajax에서 보낸 email : "+email);
		System.out.println( "=============> ajax에서 보낸 shuttlecock : "+shuttlecock);
		System.out.println( "=============> ajax에서 보낸 btnNum : "+btnNum);
		reservationService.saveReservation(principalDetails,new Reservation(name, num, time, racket, shuttlecock, email,btnNum), id);
		
	}

}
