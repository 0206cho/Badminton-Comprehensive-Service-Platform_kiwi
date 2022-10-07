package com.kiwi.market.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import com.kiwi.shop.entity.BaseEntity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "file")
@Getter
@Setter
@ToString
//@Builder
@NoArgsConstructor
@AllArgsConstructor
public class File extends BaseEntity {
	@Id
	@Column(name = "file_idx")
	private int idx; // 파일 일련번호

	@Lob
	@Column(name = "market_idx")
	private int marketIdx; // 게시글 번호

	@Lob
	@Column(name = "file_originalFileName")
	private String originalFileName; // 원본 파일 이름
	
	@Lob
	@Column(name = "file_storedFilePath")
	private String storedFilePath; // 파일 저장 경로
	
	@Column(name = "file_fileSize")
	private long fileSize; // 파일 크기

}