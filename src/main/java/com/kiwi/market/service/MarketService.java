package com.kiwi.market.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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
    MarketRepository marketRepository; // private final MarketRepository marketRepository;
    
	@Autowired
	UploadFile uploadFile;
	
    public void saveMarket(MarketDto marketDto, MultipartFile file) throws Exception{
        // 상품 등록
    	System.out.println("---------------> 서비스 접근");
    	if(file.isEmpty()) {
			String img = "";
			marketDto.setFilepath(img);
			System.out.println("===================>" + img);
		} else {
			uploadFile.fildUpload(marketDto, file);
			System.out.println("-----------------> name : " + file.getName());
			System.out.println("-----------------> size : " + file.getSize());
			System.out.println("-----------------> type : " + file.getContentType());
			System.out.println("-----------------> old name : " + file.getOriginalFilename()); 
		}
    	marketRepository.save(marketDto.toEntity());
    	
    }

	public List<Market> maketList() {

		List<Market> list =  marketRepository.findAllByOrderByIdDesc();
		
		return list;
	}

	public Market marketDetail(Long id) {
		Optional<Market> optional = marketRepository.findById(id);
		if(optional.isPresent()) {
			Market market = optional.get();
			return market;
		} else {
			throw new NullPointerException();
		}
	}


}
