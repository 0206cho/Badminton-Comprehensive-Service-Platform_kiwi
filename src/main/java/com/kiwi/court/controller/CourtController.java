package com.kiwi.court.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kiwi.court.entity.Court;
import com.kiwi.court.service.CourtService;

@RequestMapping("/court")
@Controller
public class CourtController {

	@Autowired
	private CourtService courtService;
	
	// 코트 예약 페이지
	@GetMapping(value = "/reservation")
	public String courtReservation(Model model) {
		List<Court> courtAll = courtService.getCourtAll();
		System.out.println(courtAll);
		int test = 11;
		model.addAttribute("courtList", courtAll);
		return "court/reservation";
	}
}
