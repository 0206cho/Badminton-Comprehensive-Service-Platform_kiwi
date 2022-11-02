package com.kiwi.match.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kiwi.court.repository.CourtRepository;
import com.kiwi.match.entity.Matchs;
import com.kiwi.match.repository.MatchRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class MatchService {
	
	@Autowired
	MatchRepository matchRepository;
	
	@Autowired
	CourtRepository courtRepository;
	
//	@Autowired
//	ReservationRepository reservationRepository; 

	public String courtTest(Long id){
		
	//	Reservation re = reservationRepository.findById(id).orElseThrow();
		//Court list = courtRepository.findById().orElseThrow();
		//Reservation list = matchRepository.findByReservation(reservation);
//		System.out.println(">>>>>>>>>> re : " + re);
//		System.out.println(">>>>>>>>>> re_court : " + re.getCourt());
		
		Matchs matchs = matchRepository.findById(id).orElseThrow();
		System.out.println(">>>>>>>>>> courtName : " + matchs.getReservation().getCourt().getName());
		
		String str = matchs.getReservation().getCourt().getName();
		return str;
		
	}
	
	public List<Matchs> courtTest() {
		List<Matchs> matchs = matchRepository.findAllByOrderByIdDesc();
		System.out.println(">>>>>>>>>> courtName : " + matchs.get(0).getReservation().getCourt().getName());
		
		return matchs;
	}
	
	// 매치 리스트
	public List<Matchs> matchList() {
		List<Matchs> matchs = matchRepository.findAllByOrderByIdDesc();
		//System.out.println(">>>>>>>>>> courtName : " + matchs.get(0).getReservation().getCourt().getName());
		
		
		return matchs;
	}

	

//	// 매치 신청
//	public void saveMatch(Match match) {
//		matchRepository.save(match);		
//	}

}
