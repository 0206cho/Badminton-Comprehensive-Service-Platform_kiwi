package com.kiwi.market.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
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

	@ManyToOne(fetch = FetchType.LAZY) // 마켓 엔티티와 다대일 단방향 매핑 // 지연로딩을 설정하여 매핑된 마켓 엔티티 정보가 필요한 경우 데이터를 조회
	@JoinColumn(name = "market_id")
	private Market market;

}