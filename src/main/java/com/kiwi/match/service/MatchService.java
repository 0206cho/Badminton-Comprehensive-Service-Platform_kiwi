package com.kiwi.match.service;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kiwi.court.entity.Reservation;
import com.kiwi.court.repository.CourtRepository;
import com.kiwi.court.repository.ReservationRepository;
import com.kiwi.market.entity.Market;
import com.kiwi.match.constant.Status;
import com.kiwi.match.entity.Matchs;
import com.kiwi.match.entity.MatchsReservation;
import com.kiwi.match.repository.MatchRepository;
import com.kiwi.match.repository.MatchsReservationRepository;
import com.kiwi.member.entity.Member;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class MatchService {
	
	@Autowired
	MatchRepository matchRepository;
	
	@Autowired
	CourtRepository courtRepository;
	
	@Autowired
	ReservationRepository reservationRepository;
	
	@Autowired
	MatchsReservationRepository matchsReservationRepository; 

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
	
	// 매치 리스트
	public List<Matchs> matchList() {
		List<Matchs> matchs = matchRepository.findAllByOrderByIdDesc();
		//System.out.println(">>>>>>>>>> courtName : " + matchs.get(0).getReservation().getCourt().getName());
		
		return matchs;
	}


	public List<Reservation> matchsCourt() {
		List<Reservation> reservation = reservationRepository.findAllByOrderByIdDesc();
		// TODO Auto-generated method stub
		return reservation;
	}

	public Matchs matchDetail(Long id) {
		Optional<Matchs> optional = matchRepository.findById(id);
		if (optional.isPresent()) {
			Matchs matchs = optional.get();
			return matchs;
		} else {
			throw new NullPointerException();
		}
	}


	public void saveMatchsReservation(MatchsReservation mr, Long mathcshId, Long memberId) {
		Matchs mt = matchRepository.findById(mathcshId).orElseThrow(EntityNotFoundException::new);
		System.out.println();
		mr.setMathshId(mt);
		
		mr.setMemId(memberId);
		matchsReservationRepository.save(mr);
	}

	// 매치 개설
//	public void saveMatch(Matchs matchs, Long memberId, Reservation reservation) {
//		matchs.setMemberId(memberId);
//		matchs.setReservation(reservation);
//		
//		// 단식일 경우 count2, 복식일 경우 count 4
//		if(matchs.getType().equals("1vs1(단식)")) {
//			matchs.setCount(2);			
//		}
//		else {
//			matchs.setCount(4);
//		}
//		System.out.println(">>>>>>>>>>>> match : " + matchs.getType());
//		
//		matchRepository.save(matchs);
//	}

	public void saveMatch(Matchs matchs, Long memberId, Long reservationId) {
		
		Reservation reser = reservationRepository.findById(reservationId).orElseThrow();
		matchs.setMemberId(memberId);
		matchs.setReservation(reser);
		// 단식일 경우 count2, 복식일 경우 count 4
		if(matchs.getType().equals("1vs1(단식)")) {
			matchs.setCount(2);			
		}
		else {
			matchs.setCount(4);
		}
		System.out.println(">>>>>>>>>>>> match : " + matchs.getType());
		
		matchRepository.save(matchs);
	}


//	// 매치 신청
//	public void saveMatch(Match match) {
//		matchRepository.save(match);		
//	}

}
