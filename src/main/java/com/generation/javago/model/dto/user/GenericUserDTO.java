package com.generation.javago.model.dto.user;

import java.util.List;

import com.generation.javago.model.entity.RoomBooking;
import com.generation.javago.model.entity.User;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public abstract class GenericUserDTO {

	public GenericUserDTO(User u) 
	{
		id = u.getId();
		email = u.getEmail();
		type = u.getType();
		booking = u.getBooking();
	}
	
	public User revertToUser() 
	{
		User d = User.builder()
					 .id(id)
					 .email(email)
					 .type(type)
					 .booking(booking)
				 .build();
		return d;
	}
	
	protected Integer id;
	protected String email;
	protected String type;
	protected List<RoomBooking> booking;
}
