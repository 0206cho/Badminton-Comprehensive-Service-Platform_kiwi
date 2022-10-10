package com.kiwi.market;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import com.kiwi.market.dto.MarketDto;


@Component
public class UploadFile {
	
	@Value("${resource}")
	private String path;
	
	public void fildUpload(MarketDto dto, MultipartFile file) throws IOException {
		
		UUID uuid = UUID.randomUUID();
		
		String fileName = File.separator + uuid + "_" + file.getOriginalFilename();
		
		
		File uploadFile = new File(path, fileName);
		
		uploadFile.createNewFile();
		
		FileCopyUtils.copy(file.getBytes(), uploadFile);
		
		dto.setFilename(fileName);
		dto.setFilepath(File.separator + "image\\title" + fileName);
		
	}
}
