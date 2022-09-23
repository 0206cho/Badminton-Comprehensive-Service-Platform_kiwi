package com.kiwi.market.controller;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.kiwi.market.dto.MarketDto;
import com.kiwi.market.service.MarketService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MarketController {

	private final MarketService marketService;

	@GetMapping(value = "/admin/market/new")
	public String market(Model model) {
		model.addAttribute("marketDto", new MarketDto());
		return "/market/marketForm";
	}

	@PostMapping(value = "/admin/market/new")
	public String marketNew(MarketDto marketDto, Model model) {

//        // 상품 등록 시 필수 값이 없다면 다시 상품 등록페이지로 이동
//        if(bindingResult.hasErrors()){
//            return "item/itemForm";
//        }

//        // 상품 등록시 첫번째 이미지가 없다면 에러 메시지와 함께 상품 등록 페이지로 전환합니다. 첫번째 이미지는 메인페이지에서 보여줄 이미지 이기에 필수 값으로 지정함
//        if(itemImgFileList.get(0).isEmpty() && itemFormDto.getId() == null){
//            model.addAttribute("errorMessage", "첫번째 상품 이미지는 필수 입력 값 입니다.");
//            return "item/itemForm";
//        }

		// 상품 저장 로직을 호출 // 상품을 저장할때는 상품 정보와 이미지 파일 같이 저장
//        try{
//            itemService.saveItem(itemFormDto, itemImgFileList);
//        } catch (Exception e){
//            model.addAttribute("errorMessage","상품 등록 중 에러가 발생하였습니다.");
//            return "item/itemForm";
//        }

		try {
			marketService.saveMarket(marketDto);
		} catch (Exception e) {
			model.addAttribute("errorMessage", "상품 등록 중 에러가 발생하였습니다.");
			return "market/marketForm";
		}
		// 상품이 정상적으로 등록되었다면 메인 페이지로 이동
		return "redirect:/";
	}

}
