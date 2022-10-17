package com.kiwi.pay.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.kiwi.member.entity.Member;
import com.kiwi.pay.entity.Cash;

public interface CashRepository extends JpaRepository<Cash,Long> {
	
	@Transactional
	@Query("select sum(c.amount) from Cash c where c.member = ?1")
	Integer amountSum(Member member);
}
