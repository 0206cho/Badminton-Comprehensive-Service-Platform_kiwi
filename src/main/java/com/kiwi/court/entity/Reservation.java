package com.kiwi.court.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.kiwi.member.entity.Member;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="reservation")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Reservation {
	
	@Id
    @Column(name="reservation_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
	
	// 코트 넘버
	private String court_num;
	
	// 코트 시간
	private String court_time;
	
	// 코트 예약 시간
	private LocalDateTime reservation_time;
	
	@ManyToOne
	@JoinColumn(name = "court_id")
	private Court court;
	
	@ManyToOne
	@JoinColumn(name = "member_id")
	private Member member;
	
}
