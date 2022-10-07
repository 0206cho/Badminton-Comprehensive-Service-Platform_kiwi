package com.kiwi.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Service;

import com.kiwi.config.auth.PrincipalDetails;
import com.kiwi.member.service.MemberService;
/**
 * 로그인 성공 핸들러 
 * @author Jongha
 *
 */
@Service
public class loginSuccessHandler implements AuthenticationSuccessHandler {
	
	@Autowired
    private MemberService memberService;
	
	/**
	 * 로그인 성공 메서드
	 */
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		
			PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
			System.out.println("================" + principalDetails.getUsername());
			String user = principalDetails.getUsername();
			String birthday = principalDetails.getMember().getBirthday();
			if(birthday == null) {
				response.sendRedirect("/members/login/addInfo");
				//response.sendRedirect("/");
			} else {
				response.sendRedirect("/");
			}
			
		
	}
 
}
