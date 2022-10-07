package com.kiwi.member.controller;

import lombok.RequiredArgsConstructor;

import org.apache.catalina.authenticator.SpnegoAuthenticator.AuthenticateAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kiwi.config.auth.PrincipalDetails;
import com.kiwi.constant.Address;
import com.kiwi.constant.Bank;
import com.kiwi.constant.Gender;
import com.kiwi.member.dto.MemberFormDto;
import com.kiwi.member.entity.KakaoProfile;
import com.kiwi.member.entity.Member;
import com.kiwi.member.entity.OAuthToken;
import com.kiwi.member.service.MemberService;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

@RequestMapping("/members")
@Controller
@RequiredArgsConstructor
public class MemberController {

	@Autowired
	private MemberService memberService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	// 회원 가입 로직
	@GetMapping(value = "/new")
	public String memberForm(Model model) {

		model.addAttribute("memberFormDto", new MemberFormDto());
		model.addAttribute("genders", Gender.values());
		model.addAttribute("bnames", Bank.values());
		model.addAttribute("local", Address.values());
//        List<String> genders = new ArrayList<>();
//        genders.add("남자");
//        genders.add("여자");
//        model.addAttribute("genders",genders);

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
	@GetMapping(value = "/mypage")
	public String mypage() {
		return "/member/memberMypage";
	}

	@GetMapping("/test/login")
	public @ResponseBody String testLogin(Authentication authentication,
			@AuthenticationPrincipal PrincipalDetails userDetails) {
		System.out.println("/test/login ===============");
		// PrincipalDetail
		PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
		System.out.println("authentication : " + principalDetails.getMember());

		System.out.println("userDetails : " + userDetails.getMember());
		return "세션 정보 확인하기";
	}

	@GetMapping("/test/oauth/login")
	public @ResponseBody String testLogin(Authentication authentication, @AuthenticationPrincipal OAuth2User oauth) {
		System.out.println("/test/login ===============");
		OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
		System.out.println("authentication : " + oAuth2User.getAttributes());
		System.out.println("OAuth2User : " + oauth.getAttributes());

		return "OAuth 세션 정보 확인하기";
	}
	
	@GetMapping("/login/addInfo")
	public String addInfo() {
		return "/member/memberAddInfo";
	}

}
