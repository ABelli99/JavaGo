package com.generation.javago.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.generation.javago.model.entity.Room;

public interface RoomRepository extends JpaRepository<Room, Integer>
{
	Room findByRoomName(String name); 
	
	List<Room> findByCapacity(Integer nm);
}
