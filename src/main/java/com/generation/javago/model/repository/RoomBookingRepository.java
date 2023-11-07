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

	@Query("SELECT rb FROM RoomBooking rb WHERE rb.checkInDate BETWEEN :d1 AND :d2 OR rb.checkOutDate BETWEEN :d1 AND :d2")
	List<RoomBooking> findByCheckInDateCheckOutDate(@Param("d1") LocalDate d1, @Param("d2") LocalDate d2);
	
	/**
	 * find all bookings in the specified time-zone
	 * AND by the specified user email
	 * @param LocalDate [CheckInDate]
	 * @param LocalDate [CheckOutDate]
	 * @param String email
	 * @return List<RoomBooking>
	 */
	@Query("SELECT rb FROM RoomBooking rb WHERE ((:d1 BETWEEN rb.checkInDate AND rb.checkOutDate) OR (:d2 BETWEEN rb.checkInDate AND rb.checkOutDate)) AND email = :useremail")
	List<RoomBooking> findByCheckInDateCheckOutDateEmail(@Param("d1") LocalDate d1, @Param("d2") LocalDate d2, @Param("useremail") String useremail);
	
	
	/**
	 * find all bookings with the given number of guests
	 * @param int [NumberOfGuests]
	 * @return List<RoomBooking>
	 */
	List<RoomBooking> findByNumOfGuests(int cap);
}
