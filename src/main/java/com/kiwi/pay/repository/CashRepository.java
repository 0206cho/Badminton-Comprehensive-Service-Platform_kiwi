package com.kiwi.pay.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kiwi.pay.entity.Cash;

public interface CashRepository extends JpaRepository<Cash,Long> {

}
