package com.kiwi.market.controller;

import java.io.File;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.kiwi.config.auth.PrincipalDetails;
import com.kiwi.market.dto.MarketDto;
import com.kiwi.market.dto.MarketSearchDto;
import com.kiwi.market.entity.Comment;
import com.kiwi.market.entity.Market;
import com.kiwi.market.entity.MarketLike;
import com.kiwi.market.repository.MarketRepository;
import com.kiwi.market.service.CommentService;
import com.kiwi.market.service.MarketLikeService;
import com.kiwi.market.service.MarketService;
import com.kiwi.match.dto.MatchSearchDto;
import com.kiwi.match.entity.Matchs;
import com.kiwi.member.constant.Address;
import com.kiwi.member.entity.Member;
import com.kiwi.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@RequestMapping("/market")
@Controller
@RequiredArgsConstructor
public class MarketController {

	private Logger log = LoggerFactory.getLogger(this.getClass());

	private final MarketService marketService;
	private final CommentService commentService;

	@Value("${marketImgLocation}")
	private String marketImgLocation;

	@Autowired
	private MarketRepository marketRepository;

	@Autowired
	private MemberRepository memberRepository;

	@Autowired
	private MarketLikeService mlService;

	// ?????? ????????? ??????
	@ResponseBody
	@PostMapping("/marketLike")
	public void marketLike(String id, Model model, @AuthenticationPrincipal PrincipalDetails principalDetails) {
		System.out.println(">>>>>>>>>>>>>>> ajax??? ????????? id  : " + id);
		Long marketId = Long.parseLong(id);

		Long memberId = principalDetails.getMember().getId();
		Long marketLikeId = (long)0;
		boolean count = false;  // ???????????? ???????????? ??????

		List<MarketLike> list = mlService.marketLike();
//		System.out.println(">>>>>>>>>>>> list" + list);
		
		// ?????? ???????????? ????????? ??????
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getMarketId().getId().equals(marketId)) { // ?????? ID??? DB??? ?????? ?????? -> ?????? ???????????? ?????? ??????
				if (list.get(i).getMemId() == memberId) { // ??????ID??? ?????? ID??? ?????? ?????? ??? ??????
					count = true; 
//					System.out.println(">>>>>>>>>>>>>>> " + count + marketId + "?????? ????????? ???????????????.");
					marketLikeId = list.get(i).getId();
					// ????????? ?????? ?????? ?????? ??????
					mlService.deleteMarketLike(marketLikeId);
//					System.out.println(">>>>>>>>>>>>>>>>>>> : " + marketLikeId + "???????????? ?????????????????????.");
				} 
			} 
		}
		
		// ????????? ??????
		if(!count) {
//			System.out.println(">>>>>>>>>>>>>>>>>>>>> ????????? ??????" + count);
			MarketLike ml = MarketLike.createML(memberId);
			marketService.saveMarketLike(ml, marketId, memberId);
		}
	}

	// ?????? ??? ?????? ?????????
	@GetMapping(value = "/marketNew")
	public String market(Model model) {
		model.addAttribute("marketDto", new MarketDto());
		model.addAttribute("local", Address.values());
		return "market/marketForm";
	}

	@PostMapping(value = "/marketNew")
	public String marketNew(@Valid MarketDto marketDto, BindingResult bindingResult, Model model, MultipartFile file,
			@AuthenticationPrincipal PrincipalDetails principalDetails) throws Exception {
		if (bindingResult.hasErrors()) {
			System.out.println("-------------------->???????????????");
			return "market/marketForm";
		}

		try {
			Market market = Market.createMarket(marketDto);
			String memberName = principalDetails.getMember().getName();
			String memberImage = principalDetails.getMember().getImage();
			Long memberId = principalDetails.getMember().getId();

			marketService.saveMarket(market, file, memberName, memberImage, memberId);
		} catch (Exception e) {
			model.addAttribute("errorMessage", "?????? ?????? ??? ????????? ?????????????????????.");
			return "market/marketForm";
		}
		// ????????? ??????????????? ?????????????????? ?????? ???????????? ??????
		return "redirect:/market/marketList";
	}

	@PostMapping(value = "/image/upload")
	public ModelAndView image(MultipartHttpServletRequest request) throws Exception {
		// ckeditor??? ????????? ????????? ??? ????????? ???????????? ?????? uploaded ??? url??? json ???????????? ????????? ???
		// modelandview??? ???????????? json ???????????? ??????????????? ???????????? ????????? ??????????????? jsonView ?????? ??????
		// jsonView ?????? ????????? ????????? json ???????????? ????????? ????????? @Configuration ?????????????????? ???
		// WebConfig ????????? MappingJackson2JsonView ????????? ???????????? jsonView ???????????? ???????????? bean?????? ????????????
		// ???
		ModelAndView mav = new ModelAndView("jsonView");

		// ckeditor ?????? ????????? ?????? ??? upload : [??????] ???????????? ?????? ???????????? ????????? upload?????? ?????? ????????? ?????????
		// uploadFile??? ?????????
		MultipartFile uploadFile = request.getFile("upload");

		// ????????? ???????????? ??????
		String originalFileName = uploadFile.getOriginalFilename();

		// ????????? ?????????
		String ext = originalFileName.substring(originalFileName.indexOf("."));

		// ????????? ????????? ??? ????????? ?????? ????????? ????????? ???????????? ?????? UUID??? ???????????? ?????? ????????? ?????? ????????? ??????
		String newFileName = UUID.randomUUID() + ext;

		// ????????????/upload/???????????? ?????? ??????
		String savePath = marketImgLocation + newFileName;

		// ?????????????????? ????????? ????????? ??? ?????? ????????? ???????????? ????????? ?????? ?????? ??????????????? ????????? ????????? ???????????? jsp ?????? ????????? ????????? ?????????
		// ???????????? ????????? ???????????? ???
		// ????????? savePath??? ????????? ?????? ????????? uploadPath ????????????
		String uploadPath = "/market/image/upload/" + newFileName;

		// ?????? ????????? ?????? ?????? ??????
		File file = new File(savePath);

		// ?????? ?????????
		uploadFile.transferTo(file);

		// uploaded, url ?????? modelandview??? ?????? ??????
		mav.addObject("uploaded", true); // ????????? ??????
		mav.addObject("url", uploadPath); // ????????? ????????? ??????

		return mav;
	}

	// ?????? ?????????
//	@GetMapping("/marketList")
//	public String marketList(Model model) {
//		List<Market> list = marketService.maketList();
//		model.addAttribute("list", list);
//		System.out.println(">>>>>>>>>>>>>>>>>>>>> market list : " + list);
//		return "/market/marketList";
//	}

//	// ?????? ????????? - ?????????
//	@GetMapping("/marketList")
//	public String marketList(Model model,@PageableDefault(size = 8, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,@RequestParam(required = false, defaultValue = "") String searchText) {
//		
//		Page<Market> list = marketRepository.findByTitleContainingOrDetailContaining(searchText, searchText, pageable);
//		int startPage = Math.max(1, list.getPageable().getPageNumber() - 7);
//		int endPage = Math.min(list.getTotalPages(), list.getPageable().getPageNumber() + 7);
//
//		model.addAttribute("startPage", startPage);
//		model.addAttribute("endPage", endPage);
//		model.addAttribute("markets", list);
//
//		model.addAttribute("list", list);
//		model.addAttribute("local", Address.values());
//		return "market/marketList";
//
//	}
	
	// ?????? ????????? - ?????????
	@GetMapping({"/marketList","/marketList/{page}"})
	public String marketList(MarketSearchDto marketSearchDto,Model model,@PathVariable("page") Optional<Integer> page) {
		
//		Page<Market> list = marketRepository.findByTitleContainingOrDetailContaining(searchText, searchText, pageable);
//		int startPage = Math.max(1, list.getPageable().getPageNumber() - 7);
//		int endPage = Math.min(list.getTotalPages(), list.getPageable().getPageNumber() + 7);
//
//		model.addAttribute("startPage", startPage);
//		model.addAttribute("endPage", endPage);
//		model.addAttribute("markets", list);
//
//		model.addAttribute("list", list);
		
		Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 8);
		Page<Market> markets = marketService.getSearchMarketPage(marketSearchDto, pageable);
		model.addAttribute("list", markets);
		model.addAttribute("matchSearchDto", marketSearchDto);
		model.addAttribute("maxPage", 8);
		model.addAttribute("local", Address.values());
		return "market/marketList";

	}
	
	// ?????? ?????? ?????????
	@GetMapping("/marketDetail/{id}")
	public String marketDetail(@PathVariable("id") Long id, Model model,@AuthenticationPrincipal PrincipalDetails principalDetails) {

		if (principalDetails == null) {
			principalDetails.getMember().setId(Long.valueOf(0));
//			System.out.println(principalDetails.getMember().getId());
		}

//		System.out.println(principalDetails);
		Long memberId = principalDetails.getMember().getId();
		Market market = marketService.marketDetail(id);
		model.addAttribute("market", market);
//		System.out.println(">>>>>>>>>>>> marketId: " + market.getId());
		model.addAttribute("marketDto", new MarketDto());

		// System.out.println(">>>>>>>>>>> : " + new MarketDto());
		model.addAttribute("memberId", memberId); // ?????? ????????? ??? ID

		List<Comment> list = commentService.commentList();
		model.addAttribute("list", list);
		
		String level = principalDetails.getMember().getLevel();
		model.addAttribute("level", level);
		// ----------- ????????? ?????? ----------- 
		int counts = 0;  // ???????????? ???????????? ??????

		List<MarketLike> lists = mlService.marketLike();
		
		// ?????? ???????????? ????????? ??????
		for (int i = 0; i < lists.size(); i++) {
			if (lists.get(i).getMarketId().getId().equals(id)) { // ?????? ID??? DB??? ?????? ?????? -> ?????? ???????????? ?????? ??????
				if (lists.get(i).getMemId().equals(memberId)) { // ??????ID??? ?????? ID??? ?????? ?????? ??? ??????
					counts += 1; // ????????? ?????? ??????
//					System.out.println(">>>>>>>>>>>>>>>>>>> : " + marketLikeId + "???????????? ?????????????????????.");
				} 
			} 
		}
		
		model.addAttribute("likeStatus", counts);
		System.out.println(">>>>>>> ????????? ?????? : " + counts);

		return "market/marketDetail";

	}

	// ?????? ?????? ?????????
	@GetMapping("/marketUpdate/{id}")
	public String mDetail(@PathVariable("id") Long id, Model model,
			@AuthenticationPrincipal PrincipalDetails principalDetails) {
		Long memberId = principalDetails.getMember().getId();
		Market market = marketService.marketDetail(id);
		model.addAttribute("memberId", memberId); // ?????? ????????? ??? ID
		model.addAttribute("market", market);
		model.addAttribute("local", Address.values());
		return "market/marketUpdate";
	}

	// ?????? ?????? ?????????
	@PostMapping(value = "/marketUpdate/{id}")
	public String marketUpdate(Market market, MultipartFile file, @AuthenticationPrincipal PrincipalDetails principalDetails) throws Exception {
		
		String memberName = principalDetails.getMember().getName();
		String memberImage = principalDetails.getMember().getImage();
		Long memberId = principalDetails.getMember().getId();

		marketService.saveMarket(market, file, memberName, memberImage, memberId);

		return "redirect:/market/marketList";
	}

	// ?????? ?????? ?????????
	@GetMapping(value = "/marketDelete/{id}")
	public String marketDelete(@PathVariable("id") Long id) {
		marketService.deleteMarket(id);
		return "redirect:/market/marketList";
	}

	// ?????? ?????? ?????????
	@GetMapping("/pay")
	public String marketBuy(Market market, Model model) {
		return "pay/marketBuy";
	}

	// ?????? ?????? ?????????
	@PostMapping("/pay")
	public String marketBuy(MarketDto marketDto, Model model,
			@AuthenticationPrincipal PrincipalDetails principalDetails) {
		// ?????? ??????
//		System.out.println("==============>" + marketDto.getId());
		Long id = principalDetails.getMember().getId();
		Member member = memberRepository.findMemberById(id);
		int money = member.getKiwicash();
//		System.out.println("==================>" + money);
		model.addAttribute("money", money);

		// marketDto.marketBuy(marketDto, id);

		marketDto.marketBuy(marketDto, id);
		model.addAttribute("marketDto", marketDto);
//		System.out.println(marketDto.getTitle() + " ,  " + marketDto.getPrice());

		return "pay/marketBuy";
	}

	// ?????? ??????(?????? ??????)
	@PostMapping("/pay/result")
	@ResponseBody
	public void payResult(Long id, @AuthenticationPrincipal PrincipalDetails principalDetails) {
		marketService.buyMarket(principalDetails, id);
	}

	// ?????? ??????(?????? ??????)
	@PostMapping("/pay/finalResult")
	@ResponseBody
	public void payFinal(Long id, @AuthenticationPrincipal PrincipalDetails principalDetails) {
		marketService.finalBuyMarket(id);
	}
}