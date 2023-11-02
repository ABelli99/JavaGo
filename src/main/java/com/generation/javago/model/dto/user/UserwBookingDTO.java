package com.generation.javago.model.dto.user;

import java.util.List;

import com.generation.javago.model.dto.roombooking.RoomBookingDTONoUser;
import com.generation.javago.model.entity.RoomBooking;
import com.generation.javago.model.entity.User;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * User w/ his Bookings
 * 
 * 		uses RoomBookingDTONoUser
 * 		for avoiding recursive issues
 * 
 * @author ABelli
 *
 */
@Getter
@Setter
@NoArgsConstructor
public class UserwBookingDTO extends GenericUserDTO{

	public UserwBookingDTO(User u) {
		super(u);
		bookings = u.getBooking().stream().map(rb -> new RoomBookingDTONoUser(rb)).toList();;
	}
	
	public User revertToUser() 
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

	private List<RoomBookingDTONoUser> bookings;
}
