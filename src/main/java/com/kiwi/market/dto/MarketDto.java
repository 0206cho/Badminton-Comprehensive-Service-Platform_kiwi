package com.kiwi.market.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.modelmapper.ModelMapper;

import com.kiwi.market.entity.Market;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class MarketDto {
	
    private Long id;       // 게시글 코드

    @NotBlank(message = "제목을 반드시 입력해주세요.")
    private String title;  // 게시글 제목 
    
    @NotBlank(message = "내용을 반드시 입력해주세요.")
    private String detail;  // 게시글 내용

    @NotBlank(message = "가격을 반드시 입력해주세요.")
    private String price;     // 가격
    
    private String status; // 판매 여부
    
    private String filename;
    
	private String filepath;
	
	private String oriImgName;
    
    // 상품 저장 후 수정할 때 상품 정보를 저장하는 리스트
//    private List<MarketDto> marketDtoList = new ArrayList<>();
    
    private static ModelMapper modelMapper = new ModelMapper(); 
//
    // modelMapper를 이용하여 엔티티 객체와 DTO객체 간의 데이터를 복사하여 복사한 객체를 반환해주는 메소드
    public Market createMarket(){
        return modelMapper.map(this, Market.class);
    }
//    
//    // Market 객체를 파라미터로 받아서 Market객체와 자료형과 멤버변수명이 같을때 MarketDto로 값을 복사해서 변환한다.
//    // static 메소드로 선언해서 MarketDto객체를 생성하지 않아도 사용할 수 있다.
//    public static MarketDto of(Market market){
//        return modelMapper.map(market, MarketDto.class);
//    }
    
    
    
    // builder로 하는 방식
    public Market toEntity() {
    	return Market.builder()
    			.title(title)
    			.detail(detail)
    			.price(price)
    			.status(status)
    			.filename(filename)
    			.filepath(filepath)
    			.oriImgName(oriImgName)
    			.build();
    }

	public MarketDto(Market entity) {
		this.id = entity.getId();
		this.title = entity.getTitle();
		this.detail = entity.getDetail();
		this.price = entity.getPrice();
		this.status = entity.getStatus();
		
		this.filename = entity.getFilename();
		this.filepath = entity.getFilepath();
		this.oriImgName = entity.getOriImgName();
	}

}