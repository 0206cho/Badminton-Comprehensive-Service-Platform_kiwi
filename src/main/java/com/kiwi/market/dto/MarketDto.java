package com.kiwi.market.dto;

import org.modelmapper.ModelMapper;

import com.kiwi.market.entity.Market;

import lombok.Getter;
import lombok.Setter;

@Getter 
@Setter
public class MarketDto {
	
    private Long market_id;       // 게시글 코드

    private String market_title;  // 게시글 제목 
    
    private String market_detail;  // 게시글 내용

    private int market_price;     // 가격

    private boolean market_status; // 판매 여부
    
    private static ModelMapper modelMapper = new ModelMapper();

    // modelMapper를 이용하여 엔티티 객체와 DTO객체 간의 데이터를 복사하여 복사한 객체를 반환해주는 메소드
    public Market createMarket(){
        return modelMapper.map(this, Market.class);
    }
    
// // modelMapper를 이용하여 엔티티 객체와 DTO객체 간의 데이터를 복사하여 복사한 객체를 반환해주는 메소드
//    public static Market of(Market market){
//        return modelMapper.map(market, ItemFormDto.class);
//    }

}
