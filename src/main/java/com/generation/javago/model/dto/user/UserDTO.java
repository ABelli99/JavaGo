package com.generation.javago.model.dto.user;

import com.generation.javago.model.entity.User;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserDTO extends GenericUserDTO{
	public UserDTO(User u) {
		super(u);
	}
}
