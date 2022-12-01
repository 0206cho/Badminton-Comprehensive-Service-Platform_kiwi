package com.kiwi.match.dto;

import com.kiwi.match.constant.Level;
import com.kiwi.match.constant.Status;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MatchSearchDto {
	
	private Level searchLevel;
	
	private Status searchStatus;
	
	private String searchType; 
	
	private String searchQuery = "";
}
