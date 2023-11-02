package com.generation.javago.model.dto.user;

import com.generation.javago.model.entity.User;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *  Generic User 
 * 
 * 		w/o objects,
 * 		closest to DB entity.
 * 
 * @author ABelli
 *
 */
@Getter
@Setter
@NoArgsConstructor
public abstract class GenericUserDTO {

	public GenericUserDTO(User u) 
	{
		id = u.getId();
		email = u.getEmail();
		type = u.getType();
	}
	
	public User revertToUser() 
	{
		User d = User.builder()
					 .id(id)
					 .email(email)
					 .type(type)
				 .build();
		return d;
	}
	
	protected Integer id;
	protected String email;
	protected String type;
}
