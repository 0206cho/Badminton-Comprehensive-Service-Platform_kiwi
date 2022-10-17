package com.kiwi.pay.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kiwi.member.entity.Member;
import com.kiwi.member.repository.MemberRepository;
import com.kiwi.pay.entity.Cash;
import com.kiwi.pay.repository.CashRepository;

@Service
//로직을 처리하다가 에러가 발생하면 변경된 데이터를 조직 이전으로 콜백 시켜주기 위해
@Transactional
public class CashService {
	
	@Autowired
	private CashRepository cashRepository;
	
	@Autowired
	private MemberRepository memberRepository;
	
	// 유저 캐시 충전 - dto 만들어서 작업해야하지만 시간이 없어서 일단 엔티티로 바로 접근해서 하고 추후에 dto로 변경
	public Cash chargeCash(Cash cash, Long id) {
		Member member = memberRepository.findMemberById(id);
		cash.setMember(member);
		return cashRepository.save(cash);
	}
	
	// 유저 캐시 잔액 
	public int amountSum(Member member) {
		int sum = cashRepository.amountSum(member);
		return sum;
	}
	
	// 유저 캐시 필드 매핑
	public void cashMapping(Long id) {
		Member member = memberRepository.findMemberById(id);
		member.setKiwicash(cashRepository.amountSum(member));
	}
	
}
