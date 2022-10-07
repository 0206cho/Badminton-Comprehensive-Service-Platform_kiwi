package com.kiwi.market.dto;

import lombok.Data;

@Data
public class FileDto {
	
	private int idx;
	private int marketIdx;
	private String originalFileName;
	private String storedFilePath;
	private long fileSize;
	

}
