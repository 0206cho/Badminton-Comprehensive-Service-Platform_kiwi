package com.kiwi.match.dto;

import com.kiwi.match.entity.Matchs;
import com.kiwi.member.entity.Member;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MatchsReservationDto {

	private Long id; // 매치 아이디

    private Long memId;
	
	private Long mathcshId;
    
//	private Matchs MathcshId;
	
}
