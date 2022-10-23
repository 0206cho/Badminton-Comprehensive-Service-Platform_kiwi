package com.kiwi.market.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kiwi.market.entity.Market;

@Repository
public interface MarketRepository extends JpaRepository<Market,Long>{

	List<Market> findAllByOrderByIdDesc();

}