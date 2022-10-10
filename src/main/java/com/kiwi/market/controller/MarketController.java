package com.kiwi.market.controller;

import java.io.File;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.kiwi.market.dto.MarketDto;
import com.kiwi.market.entity.Market;
import com.kiwi.market.service.MarketService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MarketController {
	
	private Logger log = LoggerFactory.getLogger(this.getClass());

	private final MarketService marketService;
	 
	@Value("${marketImgLocation}")
    private String marketImgLocation;

	@GetMapping(value = "/admin/market/new")
	public String market(Model model) {
		model.addAttribute("marketDto", new MarketDto());
		return "/market/marketForm";
	}

	@PostMapping(value = "/admin/market/new")
	public String marketNew(MarketDto marketDto, Model model, MultipartFile file) throws Exception{
		try {
			marketService.saveMarket(marketDto, file);
		} catch (Exception e) {
			model.addAttribute("errorMessage", "상품 등록 중 에러가 발생하였습니다.");
			return "market/marketForm";
		}
		// 상품이 정상적으로 등록되었다면 메인 페이지로 이동
		return "redirect:/marketList"; // return "redirect:/";
	}			
	
	@PostMapping(value = "/image/upload")
	public ModelAndView image(MultipartHttpServletRequest request) throws Exception {

		// ckeditor는 이미지 업로드 후 이미지 표시하기 위해 uploaded 와 url을 json 형식으로 받아야 함
		// modelandview를 사용하여 json 형식으로 보내기위해 모델앤뷰 생성자 매개변수로 jsonView 라고 써줌
		// jsonView 라고 쓴다고 무조건 json 형식으로 가는건 아니고 @Configuration 어노테이션을 단 
		// WebConfig 파일에 MappingJackson2JsonView 객체를 리턴하는 jsonView 매서드를 만들어서 bean으로 등록해야 함 
		ModelAndView mav = new ModelAndView("jsonView");

		// ckeditor 에서 파일을 보낼 때 upload : [파일] 형식으로 해서 넘어오기 때문에 upload라는 키의 밸류를 받아서 uploadFile에 저장함
		MultipartFile uploadFile = request.getFile("upload");

		// 파일의 오리지널 네임
		String originalFileName = uploadFile.getOriginalFilename();
		
        // 파일의 확장자
		String ext = originalFileName.substring(originalFileName.indexOf("."));
		
        // 서버에 저장될 때 중복된 파일 이름인 경우를 방지하기 위해 UUID에 확장자를 붙여 새로운 파일 이름을 생성
		String newFileName = UUID.randomUUID() + ext;

		// 현재경로/upload/파일명이 저장 경로
		String savePath = marketImgLocation + newFileName;

		// 브라우저에서 이미지 불러올 때 절대 경로로 불러오면 보안의 위험 있어 상대경로를 쓰거나 이미지 불러오는 jsp 또는 클래스 파일을 만들어 가져오는 식으로 우회해야 함
		// 때문에 savePath와 별개로 상대 경로인 uploadPath 만들어줌
		String uploadPath = "/image/upload/" + newFileName; 

		// 저장 경로로 파일 객체 생성
		File file = new File(savePath);

		// 파일 업로드
		uploadFile.transferTo(file);
		
		// uploaded, url 값을 modelandview를 통해 보냄
		mav.addObject("uploaded", true); // 업로드 완료
		mav.addObject("url", uploadPath); // 업로드 파일의 경로

		return mav;
	}
	
	@GetMapping("/marketList")
	public String marketList(Model model) {
		List<Market> list = marketService.maketList();
		model.addAttribute("list", list);
		return "/market/marketList";
	}
	
	@GetMapping("/market/marketDetail/{id}")
	public String marketDetail(@PathVariable("id") Long id, Model model) {
		Market market = marketService.marketDetail(id);
		model.addAttribute("market", market);
		return "/market/marketDetail";
	}

}