package com.kiwi.court.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kiwi.court.entity.Reservation;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
		
}
