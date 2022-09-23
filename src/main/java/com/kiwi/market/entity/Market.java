package com.kiwi.market.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import com.kiwi.constant.MarketSellStatus;
import com.kiwi.shop.entity.BaseEntity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="market")
@Getter
@Setter
@ToString
public class Market extends BaseEntity{
	@Id
    @Column(name="market_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long market_id;       // 게시글 코드

	@Lob
    @Column
    private String market_title;  // 게시글 제목 
    
    @Lob
    @Column
    private String market_detail;  // 게시글 내용

    @Column
    private int market_price;     // 가격

    @Enumerated(EnumType.STRING)   //@Enumrated는 enum타입 매핑시 사용함!
    private MarketSellStatus marketSellStatus;  //상품 판매 상태
    
}
