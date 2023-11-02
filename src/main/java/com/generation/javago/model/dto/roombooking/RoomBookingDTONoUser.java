package com.generation.javago.model.dto.roombooking;

import com.generation.javago.model.entity.Room;
import com.generation.javago.model.entity.RoomBooking;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
/**
 * RoomBooking w/o User
 * @author ABelli
 *
 */
public class RoomBookingDTONoUser extends GenericRoomBookingDTO
{
	protected Room room;
	
	public RoomBookingDTONoUser(RoomBooking rb) 
	{
		super(rb);
		room = rb.getRoom();
	}
	
	public RoomBooking revertToRoomBooking() 
	{
			
		RoomBooking rb = super.revertToRoomBooking();
		
		rb.setRoom(room);
		
		return rb;
	}
}
