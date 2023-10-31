package com.generation.javago.model.dto.roombooking;

import com.generation.javago.model.entity.Room;
import com.generation.javago.model.entity.RoomBooking;
import com.generation.javago.model.entity.User;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RoomBookingDTOFull extends GenericRoomBookingDTO
{
	protected Room room;
	protected User user;
	
	public RoomBookingDTOFull(RoomBooking rb) 
	{
		super(rb);
		user = rb.getUser();
		room = rb.getRoom();
	}
	
	public RoomBooking revertToRoomBooking() 
	{
			
		RoomBooking rb = super.revertToRoomBooking();
		
		rb.setRoom(room);
		rb.setUser(user);
		
		return rb;
	}
	
	
	
}
