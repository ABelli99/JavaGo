package com.generation.javago.model.dto.user;

import java.util.List;

import com.generation.javago.model.dto.roombooking.RoomBookingDTOFull;
import com.generation.javago.model.entity.RoomBooking;
import com.generation.javago.model.entity.User;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserwBookingDTO extends GenericUserDTO{

	public UserwBookingDTO(User u) {
		super(u);
	}
	
	public User revertToClient() 
	{
		User u = super.revertToUser();
		u.setBooking(
					bookings.stream().map(dto->
											{
												RoomBooking rb = dto.revertToRoomBooking();
												return rb;
											}
										).toList()
									);
		
		return u;
	}

	private List<RoomBookingDTOFull> bookings;
}
