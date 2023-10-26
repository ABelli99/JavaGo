package com.generation.javago.auth.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "login")
@Getter
@Setter
public class UserInDb {
	@Id
	private String username;

	private String password;

}
