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
    
    public Market saveMarket(MarketDto marketDto){
        // 상품 등록
    	
         //marketRepository.save(marketDto.toEntity());                  // 상품 데이터 저장 - builderd일 경우
    	
    	Market market = marketDto.createMarket();       // 상품 등록 폼으로부터 입력 받은 데이터를 이용하여 item 객체를 생성

        return marketRepository.save(market); 
    }

	public List<Market> maketList() {
		System.out.println("===============================================> 11111서비스");

		List<Market> list =  marketRepository.findAllByOrderByIdDesc();
		System.out.println("===============================================> 서비스");
		
		return list;
	}


}
