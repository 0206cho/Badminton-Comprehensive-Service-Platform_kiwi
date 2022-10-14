package com.kiwi.pay.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

import com.kiwi.member.constant.Address;
import com.kiwi.member.constant.Bank;
import com.kiwi.member.constant.Gender;
import com.kiwi.member.constant.Role;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="cash")
@Getter @Setter
@NoArgsConstructor
@ToString
public class Cash {
	
	@Id
    @Column(name="cash_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
	
	// 캐시 충전 금액
	private int amount;
	
	// 캐시 충전 시간 
	private String time;
	
	// 고유 ID
	private String imp_uid;
	
	// 상점 거래 ID
	private String merchant_uid;
	
	// 카드 승인 번호 
	private String apply_num;
	
}
