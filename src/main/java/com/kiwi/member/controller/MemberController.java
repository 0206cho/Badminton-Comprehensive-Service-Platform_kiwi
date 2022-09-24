package com.kiwi.member.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kiwi.constant.Address;
import com.kiwi.constant.Bank;
import com.kiwi.constant.Gender;
import com.kiwi.member.dto.MemberFormDto;
import com.kiwi.member.entity.Member;
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
    public String memberForm(Model model){
    	
        model.addAttribute("memberFormDto", new MemberFormDto());
        model.addAttribute("genders",Gender.values());
        model.addAttribute("bnames", Bank.values());
        model.addAttribute("local", Address.values());
//        List<String> genders = new ArrayList<>();
//        genders.add("남자");
//        genders.add("여자");
//        model.addAttribute("genders",genders);
        
        return "member/memberForm"; 
    }
    
    @PostMapping(value = "/new")
    public String memberForm(@Valid MemberFormDto memberFormDto, BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()){
        	System.out.println("============================바인딩에러");
        	model.addAttribute("genders",Gender.values());
            model.addAttribute("bnames", Bank.values());
            model.addAttribute("local", Address.values());
            return "member/memberForm";
        }
        try{
            Member member = Member.createMember(memberFormDto,passwordEncoder);
            memberService.saveMember(member);
        }
        catch (IllegalStateException e){
            model.addAttribute("errorMessage", e.getMessage());
            return "member/memberForm";
        }
        return "redirect:/members/login";
    }

//    //로그인 로직
//    @GetMapping(value = "/login")
//    public String loginMember(){
//        return "/member/memberLoginForm";
//    }
    //로그인 로직
    @GetMapping(value = "/login")
    public String loginMember(){
        return "/member/memberLoginForm";
    }
    
    
    @GetMapping(value = "/login/error")
    public String loginError(Model model){
        model.addAttribute("loginErrorMsg","아이디 또는 비밀번호를 확인해주세요");
        return "/member/memberLoginForm";
    }

    // 마이페이지
    @GetMapping(value = "/mypage")
    public String mypage(){
        return "/member/memberMypage";
    }



}
