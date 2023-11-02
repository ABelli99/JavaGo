package com.generation.javago.model.dto.roombooking;

import com.generation.javago.model.entity.RoomBooking;
import com.generation.javago.model.entity.User;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor

/**
 * RoomBooking w/o Room
 * @author ABelli
 *
 */
public class RoomBookingDTONoRoom extends GenericRoomBookingDTO
{
	protected User user;
	
	public RoomBookingDTONoRoom(RoomBooking rb) 
	{
		super(rb);
		user = rb.getUser();
	}
	
	public RoomBooking revertToRoomBooking() 
	{
			
		RoomBooking rb = super.revertToRoomBooking();
		
		rb.setUser(user);
		
		return rb;
	}
}
