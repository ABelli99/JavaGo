package com.generation.javago.model.dto.roombooking;

import com.generation.javago.model.entity.Room;
import com.generation.javago.model.entity.RoomBooking;
import com.generation.javago.model.entity.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RoomBookingDTONoObj extends GenericRoomBookingDTO
{
	
	public RoomBookingDTONoObj(RoomBooking rb) 
	{
		super(rb);
	}
}
