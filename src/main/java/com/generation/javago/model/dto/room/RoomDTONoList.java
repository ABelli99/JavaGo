package com.generation.javago.model.dto.room;

import com.generation.javago.model.entity.Room;

/**
 * generic DTO (Data Transfer Obj) for room
 * only have rooms and no bookings of that room
 * @author Gory, ABelli
 *
 */
public class RoomDTONoList extends GenericRoomDTO
{
	public RoomDTONoList() {}
	
	public RoomDTONoList(Room room)
	{
		super(room);
	}
	
}
