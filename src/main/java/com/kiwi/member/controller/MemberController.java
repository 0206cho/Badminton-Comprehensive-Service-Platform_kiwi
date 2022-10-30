package com.kiwi.member.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kiwi.config.auth.PrincipalDetails;
import com.kiwi.member.constant.Address;
import com.kiwi.member.constant.Bank;
import com.kiwi.member.constant.Gender;
import com.kiwi.member.dto.MemberFormDto;
import com.kiwi.member.dto.OauthAddInfoDto;
import com.kiwi.member.entity.Member;
import com.kiwi.member.service.MemberService;
import com.kiwi.pay.repository.CashRepository;

import lombok.RequiredArgsConstructor;

@RequestMapping("/members")
@Controller
@RequiredArgsConstructor
public class MemberController {

	@Autowired
	private MemberService memberService;

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private CashRepository cashRepository;
	
	
	// 회원 가입 로직
	@GetMapping(value = "/new")
	public String memberForm(Model model) {
		model.addAttribute("memberFormDto", new MemberFormDto());
		model.addAttribute("genders", Gender.values());
		model.addAttribute("bnames", Bank.values());
		model.addAttribute("local", Address.values());
		return "member/memberForm";
	}

	@PostMapping(value = "/new")
	public String memberForm(@Valid MemberFormDto memberFormDto, BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			System.out.println("============================바인딩에러");
			model.addAttribute("genders", Gender.values());
			model.addAttribute("bnames", Bank.values());
			model.addAttribute("local", Address.values());
			return "member/memberForm";
		}
		try {
			Member member = Member.createMember(memberFormDto, passwordEncoder);
			memberService.saveMember(member);
			model.addAttribute("Message", "회원가입이 완료되었습니다.");
		} catch (IllegalStateException e) {
			model.addAttribute("genders", Gender.values());
			model.addAttribute("bnames", Bank.values());
			model.addAttribute("local", Address.values());
			System.out.println("에러 메시지 전이야!");
			model.addAttribute("errorMessage", e.getMessage());
			System.out.println("에러 메시지 후야!");
			return "member/memberForm";
		}
		return "redirect:/members/login";
	}

	// 로그인 로직
	@GetMapping(value = "/login")
	public String loginMember() {
		return "/member/memberLoginForm";
	}

	@GetMapping(value = "/login/error")
	public String loginError(Model model) {
		model.addAttribute("loginErrorMsg", "아이디 또는 비밀번호를 확인해주세요");
		return "/member/memberLoginForm";
	}

	// 마이페이지
	@GetMapping("/mypage")
	public String mypage(@AuthenticationPrincipal PrincipalDetails principalDetails, Model model) {
		Member member = memberService.mypageInfo(principalDetails);
		//System.out.println("================> 캐시 금액 : " + cashRepository.amountSum(member));	// 추후 수정
		//member.setKiwicash(cashRepository.amountSum(member));
		
		
//		Integer userCash = cashRepository.amountSum(member);
//		if(cashRepository.amountSum(member) != null) {
//			System.out.println("=================> 0") ;
//		}else {
//			System.out.println("================> tt");
//		}
		
		
		model.addAttribute("member", member);
		return "mypage/mypageMain";
	}
    
	// 소셜로그인 추가정보
	@GetMapping("/login/addInfo")
	public String addInfo(Model model,@AuthenticationPrincipal PrincipalDetails principalDetails) {
		model.addAttribute("oauthAddInfoDto", new OauthAddInfoDto());
		model.addAttribute("bnames",Bank.values());
		model.addAttribute("local",Address.values());
		return "member/memberAddInfo";
	}
	 
	// 소셜로그인 추가정보 등록
	@PostMapping("/login/addInfo")
	public String addInfo(@AuthenticationPrincipal PrincipalDetails principalDetails, OauthAddInfoDto oauthAddInfoDto, Model model) {
		memberService.addInfo(principalDetails, oauthAddInfoDto);
		return "redirect:/";
	}

	
	// form로그인 테스트
	@GetMapping("/test/login")
	@ResponseBody
	public String testLogin(Authentication authentication,
			@AuthenticationPrincipal PrincipalDetails principalDetails2) {
		System.out.println("/test/login ===============");
		// PrincipalDetail
		PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
		System.out.println("authentication : " + principalDetails.getMember());
		System.out.println("userDetails : " + principalDetails2.getMember());
		return "세션 정보 확인하기";
	}
	
	// 소셜로그인 테스트
	@GetMapping("/test/oauth/login")
	@ResponseBody
	public String testLogin(Authentication authentication, @AuthenticationPrincipal OAuth2User oauth) {
		System.out.println("/test/oauth/login ===============");
		OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
		System.out.println("authentication : " + oAuth2User.getAttributes());
		System.out.println("OAuth2User : " + oauth.getAttributes());

		return "OAuth 세션 정보 확인하기";
	}
	

	
	
}
