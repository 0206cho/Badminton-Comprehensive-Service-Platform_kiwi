package com.kiwi.market.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import com.kiwi.shop.entity.BaseEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "market")
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Market extends BaseEntity {
	@Id
	@Column(name = "market_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id; // 게시글 코드

	@Lob
	@Column(name = "market_title")
	private String title; // 게시글 제목

	@Lob
	@Column(name = "market_detail")
	private String detail; // 게시글 내용

	@Column(name = "market_price")
	private String price; // 가격

	@Column(name = "market_status")
	private String status; // 판매 여부
	
	private String filename;
	
	private String filepath;
	 
	// 원본 이미지 파일명
	@Column(name = "oriImgName")
	private String oriImgName;

//  @Enumerated(EnumType.STRING)   //@Enumrated는 enum타입 매핑시 사용함!
//  private MarketSellStatus marketSellStatus;  //상품 판매 상태

}