package com.generation.javago.model.dto.room;

import java.util.List;

import com.generation.javago.model.dto.roombooking.RoomBookingDTOFull;
import com.generation.javago.model.entity.Room;
import com.generation.javago.model.entity.RoomBooking;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoomDTOFull extends GenericRoomDTO
{
	
	private List<RoomBookingDTOFull> booking;
	
	public RoomDTOFull() {}
	
	public RoomDTOFull(Room room)
	{
		super(room);
		booking = room.getBooking().stream().map(rb -> new RoomBookingDTOFull(rb)).toList();
	}
	
	@Override
	public Room convertToRoom()
	{
		Room room = new Room();
		
		room.setRooms
		(
				booking.stream()
				.map(RBDTOFull ->
						{
							RoomBooking roombooking = RBDTOFull.convertToRoomBooking();
							roombooking.setRoom(room);
							return roombooking;
						}						
					).toList()	
		);
		return room;
	}
	
	
}
