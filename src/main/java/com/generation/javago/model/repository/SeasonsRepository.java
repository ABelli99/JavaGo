package com.generation.javago.model.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.generation.javago.model.entity.Season;

public interface SeasonsRepository extends JpaRepository<Season, Integer>
{
	/**
	 * find all Seasons in the specified time-zone
	 * @param LocalDate [startDate]
	 * @param LocalDate [endDate]
	 * @return List<Season>
	 */
	@Query("SELECT rb FROM Season rb WHERE (:d1 BETWEEN rb.start_date AND rb.end_date) OR (:d2 BETWEEN rb.start_date AND rb.end_date)")
	List<Season> findByStartAndEndDate(@Param("d1") LocalDate d1, @Param("d2") LocalDate d2);
	
}
