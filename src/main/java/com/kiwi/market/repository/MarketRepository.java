package com.kiwi.market.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kiwi.market.entity.Market;

public interface MarketRepository extends JpaRepository<Market,Long>{

}
