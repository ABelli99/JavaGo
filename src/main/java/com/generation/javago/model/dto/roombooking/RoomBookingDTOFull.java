package com.generation.javago.model.dto.roombooking;

import com.generation.javago.model.entity.RoomBooking;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RoomBookingDTOFull extends GenericRoomBookingDTO
{
	public RoomBookingDTOFull(RoomBooking rb) 
	{
		super(rb);
	}
}
