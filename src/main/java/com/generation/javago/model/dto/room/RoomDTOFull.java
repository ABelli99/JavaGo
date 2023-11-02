package com.generation.javago.model.dto.room;

import java.util.List;

import com.generation.javago.model.dto.roombooking.RoomBookingDTOFull;
import com.generation.javago.model.dto.roombooking.RoomBookingDTONoRoom;
import com.generation.javago.model.entity.Room;
import com.generation.javago.model.entity.RoomBooking;

import lombok.Getter;
import lombok.Setter;

/**
 * generic DTO (Data Transfer Obj) for room
 * have everything room entity have (list of bookings)
 * 
 * 		uses RoomBookingDTONoRoom
 * 		for avoiding recursive issues
 * 
 * @author Gory, ABelli
 *
 */
@Getter
@Setter
public class RoomDTOFull extends GenericRoomDTO
{
	
	private List<RoomBookingDTONoRoom> booking;
	
	public RoomDTOFull() {}
	
	public RoomDTOFull(Room room)
	{
		super(room);
		booking = room.getBooking().stream().map(rb -> new RoomBookingDTONoRoom(rb)).toList();
	}
	
	@Override
	public Room convertToRoom()
	{
		Room room = new Room();
		
		room.setBooking
		(
				booking.stream()
				.map(RBDTOFull ->
						{
							RoomBooking roombooking = RBDTOFull.revertToRoomBooking();
							roombooking.setRoom(room);
							return roombooking;
						}						
					).toList()	
		);
		return room;
	}
	
	
}
