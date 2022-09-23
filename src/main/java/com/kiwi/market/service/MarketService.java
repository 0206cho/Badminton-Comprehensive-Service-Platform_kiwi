package com.kiwi.market.service;

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
    private final MarketRepository marketRepository;
    
    public Market saveMarket(MarketDto marketDto) throws Exception{
        // 상품 등록
    	Market market = marketDto.createMarket();       // 상품 등록 폼으로부터 입력 받은 데이터를 이용하여 item 객체를 생성

        return marketRepository.save(market);                  // 상품 데이터 저장

    }


}
