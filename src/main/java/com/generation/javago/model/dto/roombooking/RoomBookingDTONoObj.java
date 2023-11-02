package com.generation.javago.model.dto.roombooking;

import com.generation.javago.model.entity.RoomBooking;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
/**
 * RoomBooking w/o any objects
 * @author ABelli
 *
 */
public class RoomBookingDTONoObj extends GenericRoomBookingDTO
{
	
	public RoomBookingDTONoObj(RoomBooking rb) 
	{
		super(rb);
	}
}
