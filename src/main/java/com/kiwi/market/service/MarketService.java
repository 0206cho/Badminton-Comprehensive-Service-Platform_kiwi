package com.kiwi.market.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kiwi.market.dto.MarketDto;
import com.kiwi.market.entity.Market;
import com.kiwi.market.repository.MarketRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class MarketService {
	
	@Autowired
    MarketRepository marketRepository; // private final MarketRepository marketRepository;
    
    public void saveMarket(MarketDto marketDto){
        // 상품 등록
    	//Market market = marketDto.createMarket();       // 상품 등록 폼으로부터 입력 받은 데이터를 이용하여 item 객체를 생성
    	
         marketRepository.save(marketDto.toEntity());                  // 상품 데이터 저장
    }

	public List<MarketDto> maketList() {
		System.out.println("===============================================> 11111서비스");

		List<Market> list =  marketRepository.findAll();
		System.out.println("===============================================> 서비스");
		
		return list.stream().map(MarketDto::new).collect(Collectors.toList());
	}


}
