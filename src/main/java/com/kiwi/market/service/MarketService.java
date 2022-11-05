package com.kiwi.market.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.kiwi.market.UploadFile;
import com.kiwi.market.dto.MarketDto;
import com.kiwi.market.entity.Market;
import com.kiwi.market.repository.MarketRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class MarketService {

	@Autowired
	MarketRepository marketRepository;

	@Autowired
	UploadFile uploadFile;

	public void saveMarket(Market market, MultipartFile file, String memberName, String memberImage, Long memberId) throws Exception {
		if (file.isEmpty()) {
			String img = "";
			market.setFilepath(img);
			System.out.println(">>>>>>>>>>>>>>>>>> img : " + img);
		} else {
			uploadFile.fildUpload(market, file);
		}

		market.setMemId(memberId);
		market.setMemName(memberName);
		market.setMemImg(memberImage);
		
		marketRepository.save(market);
	}

//	public void saveMarket(Market market, MultipartFile file) throws Exception { // 마켓 글 등록
//		if (file.isEmpty()) {
//			String img = "";
//			market.setFilepath(img);
//		} else {
//			uploadFile.fildUpload(market, file);
//		}
//
//		market.setMemId(null);
//		marketRepository.save(market);
//
//	}

//    public void saveMarket(Market market) {
//        // 상품 등록    	
//    	marketRepository.save(market);
//    	
//    }

	public List<Market> maketList() {

		List<Market> list = marketRepository.findAllByOrderByIdDesc();

		return list;
	}

	public Market marketDetail(Long id) {
		Optional<Market> optional = marketRepository.findById(id);
		if (optional.isPresent()) {
			Market market = optional.get();
			return market;
		} else {
			throw new NullPointerException();
		}
	}

	// 수정
	public Long updateMarket(Market market, MultipartFile file) throws IOException {
		if (file.isEmpty()) {
			String img = "";
			market.setFilepath(img);
		} else {
			uploadFile.fildUpload2(market, file);
		}
		return marketRepository.save(market).getId();
	}

	// 마켓 글 삭제
	public void deleteMarket(Long id) {
		marketRepository.deleteById(id);
	}

	// 마켓 조회 -> 페이지
	public Page<Market> findAll(PageRequest of) {
		return null;
	}

	// 마켓 조회 -> 페이지 -> 검색 (최종 사용)
//	public Page<Market> findByTitleContainingOrContentContaining(String search, String search2, Pageable pageable) {
//		return marketRepository.findByTitleContainingOrContentContaining(title, content, pageable);
//	}

}
