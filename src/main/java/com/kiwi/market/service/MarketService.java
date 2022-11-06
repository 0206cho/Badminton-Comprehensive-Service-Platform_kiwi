package com.kiwi.market.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.kiwi.config.auth.PrincipalDetails;
import com.kiwi.court.entity.Court;
import com.kiwi.market.UploadFile;
import com.kiwi.market.constant.ItemSellStatus;
import com.kiwi.market.dto.MarketDto;
import com.kiwi.market.entity.Market;
import com.kiwi.market.repository.MarketRepository;
import com.kiwi.member.entity.Member;
import com.kiwi.member.repository.MemberRepository;
import com.kiwi.pay.service.CashService;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class MarketService {

	@Autowired
	MarketRepository marketRepository;

	@Autowired
	UploadFile uploadFile;

	@Autowired
	MemberRepository memberRepository;
	
	@Autowired
	private CashService cashService;

	public void saveMarket(Market market, MultipartFile file, String memberName, String memberImage, Long memberId) throws Exception {
		if (file.isEmpty()) {
			String img = "";
			market.setFilepath(img);
		} else {
			uploadFile.fildUpload(market, file);
		}

		market.setMemId(memberId);
		market.setMemName(memberName);
		market.setMemImg(memberImage);
		market.setStatus(ItemSellStatus.판매중);
		System.out.println(">>>>>>>>>> 판매중 : " + market.getStatus());
		marketRepository.save(market);
	}

	// 마켓 리스트
	public List<Market> maketList() {

		List<Market> list = marketRepository.findAllByOrderByIdDesc();

		return list;
	}

	// 마켓 상세 조회
	public Market marketDetail(Long id) {
		Optional<Market> optional = marketRepository.findById(id);
		if (optional.isPresent()) {
			Market market = optional.get();
			return market;
		} else {
			throw new NullPointerException();
		}
	}

	// 마켓 글 삭제
	public void deleteMarket(Long id) {
		marketRepository.deleteById(id);
	}

	// 마켓 조회 -> 페이지
	public Page<Market> findAll(PageRequest of) {
		return null;
	}

	// 마켓 구매하기
	public MarketDto getMarketDtl(Long marketId) {
		Market market = marketRepository.findById(marketId).orElseThrow(EntityNotFoundException::new);
		MarketDto dto = MarketDto.of(market);
		return dto;
	}

	// 마켓 구매 완료하기
	public Market saveBuyMarket(@AuthenticationPrincipal PrincipalDetails principalDetails, Market market, Long id) {
		Long memid = principalDetails.getMember().getId();
		market.setBuy_memId(memid);
		cashService.cashDeposit(memid, 20000);
		
		return marketRepository.save(market);
		
	}

}
