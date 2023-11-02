package com.generation.javago.model.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.generation.javago.model.entity.Room;
import com.generation.javago.model.entity.RoomBooking;

/**
 * repository for RoomBooking
 * 
 * @author ABelli
 *
 */

public interface RoomBookingRepository extends JpaRepository<RoomBooking, Integer>
{
	/**
	 * find all bookings of a single room
	 * @param room
	 * @return List<RoomBooking>
	 */
	List<RoomBooking> findByRoom(Room room);
	
	/**
	 * find all bookings in the specified time-zone
	 * @param LocalDate [CheckInDate]
	 * @param LocalDate [CheckOutDate]
	 * @return List<RoomBooking>
	 */
	@Query("SELECT rb FROM RoomBooking rb WHERE rb.checkInDate >= :d1 AND rb.checkOutDate <= :d2")
	List<RoomBooking> findByCheckInDateCheckOutDate(@Param("d1") LocalDate d1, @Param("d2") LocalDate d2);
	
	/**
	 * find all bookings with the given number of guests
	 * @param int [NumberOfGuests]
	 * @return List<RoomBooking>
	 */
	List<RoomBooking> findByNumOfGuests(int cap);
}
