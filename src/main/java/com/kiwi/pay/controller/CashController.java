package com.kiwi.pay.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping("/cash")
@Controller
public class CashController {
	
	// 결제페이지
    @GetMapping(value = "/pay")
    public String pay(){
    	return "pay/pay";
    }
	
    // 결제 테스트페이지
	@GetMapping("/test/pay")
	@ResponseBody
	public void pay(Long amount,String merchant_uid, String imp_uid, String paid_amount, String apply_num) {
		System.out.println("============ 결제 금액 : " + amount);
		System.out.println("============ 상점 거래 ID : " + merchant_uid);
		System.out.println("============ 고유ID : " + imp_uid);
		System.out.println("============ 결제 금액 : " + paid_amount);
		System.out.println("============ 카드 승인번호 : " + apply_num);
	}
	
}
