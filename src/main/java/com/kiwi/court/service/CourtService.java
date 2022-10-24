package com.kiwi.court.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kiwi.court.dto.CourtDto;
import com.kiwi.court.entity.Court;
import com.kiwi.court.repository.CourtRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
public class CourtService {
	
	@Autowired
	private CourtRepository courtRepository;
	
	public List<Court> getCourtAll() {
		List<Court> findAll = courtRepository.findAll();
		return findAll;
	}
//	public void testBBB() {
//		System.out.println("===========>" + courtRepository.getLat());
//	}
	
}
