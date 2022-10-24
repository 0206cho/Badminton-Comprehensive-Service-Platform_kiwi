package com.kiwi.market.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kiwi.market.entity.Market;

@Repository
public interface MarketRepository extends JpaRepository<Market,Long>{

	List<Market> findAllByOrderByIdDesc();
	
	// 검색 - 제목, 내용
	Page<Market> findByTitleContainingOrDetailContaining(String title, String detail, Pageable pageable);
}