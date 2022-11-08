package com.kiwi.match.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.kiwi.court.entity.Court;
import com.kiwi.court.entity.Reservation;
import com.kiwi.match.entity.Matchs;
import com.kiwi.match.entity.MatchsReservation;

public interface MatchRepository extends JpaRepository<Matchs, Long>{

	// 매치리스트
//	List<Match> findAllByOrderByIdDesc();
	
//	@Query("select m "
//			+ "from Matchs m "
//			+ "join m.reservation r "
//			+ "order by m.id desc")
	List<Matchs> findAllByOrderByIdDesc();

	Page<Matchs> findByRetimeContaining(String retime, Pageable pageable);

//	Reservation findByReservation(Long reservation);
	
}
