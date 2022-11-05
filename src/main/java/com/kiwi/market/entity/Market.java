package com.kiwi.market.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.kiwi.market.constant.ItemSellStatus;
import com.kiwi.market.dto.MarketDto;
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

	@Enumerated(EnumType.STRING)
	@Column(name = "market_status")
	private ItemSellStatus status; // 판매 여부
	
	private String filename;
	
	private String filepath;
	 
	// 원본 이미지 파일명
	@Column(name = "oriImgName")
	private String oriImgName;
	
	// 댓글 - 마켓 테이블에 댓글 리스트를 추가. 
	// mappendBy : 연관관계의 주인이 아니므로 DB의 FK가 아님
	// DB에는 하나의 raw 데이터에 하나의 값만 들어갈 수 있음. 
	// 만약 여러 개의 데이터가 들어간다면 원자성이 깨지므로 commentList는 DB에 FK로 생성되면 안되기 때문에 mappedBy를 사용
	@OneToMany(mappedBy = "market", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
	private List<Comment> commentList;
	
	@Column(name = "market_memId")
	private Long memId;  // 작성자 id
	
	@Column(name = "market_memName")
	private String memName;  // 작성자 이름
	
	@Column(name = "market_memImg")
	private String memImg;  // 작성자 프로필 사진
	
	
	public static Market createMarket(MarketDto marketDto) {
		Market market = new Market();
		market.setTitle(marketDto.getTitle());
		market.setDetail(marketDto.getDetail());
		market.setPrice(marketDto.getPrice());
		market.setStatus(ItemSellStatus.판매중);
		market.setFilename(marketDto.getFilename());
		market.setFilepath(marketDto.getFilepath());
		market.setOriImgName(marketDto.getOriImgName());
		
		market.setMemId(marketDto.getMemId());
		market.setMemName(marketDto.getMemName());
		market.setMemImg(marketDto.getMemImg());
		return market;
	}
	
	
	public void updateMarket(MarketDto marketDto) {
		this.title = marketDto.getTitle();
		this.detail = marketDto.getDetail();
		this.price = marketDto.getPrice();
		this.status = marketDto.getStatus();
		this.filename = marketDto.getFilename();
		this.filepath = marketDto.getFilepath();
		this.oriImgName = marketDto.getOriImgName();
		
		this.memId = marketDto.getMemId();
		this.memName = marketDto.getMemName();
		this.memImg = marketDto.getMemImg();
	}
}