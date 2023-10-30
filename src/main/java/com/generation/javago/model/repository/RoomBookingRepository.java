package com.generation.javago.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.generation.javago.model.entity.RoomBooking;

public interface RoomBookingRepository extends JpaRepository<RoomBooking, Integer>
{
	List<RoomBooking> findByRoom(String name);
}
