package com.generation.javago.model.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.generation.javago.model.entity.User;


/**
 * repository for user
 * called User2Repository because UserRepository was already used in the auth app
 * @author ABelli
 *
 */
public interface User2Repository extends JpaRepository<User, Integer>
{
	/**
	 * Method for finding user by the email
	 * @param String [email]
	 * @return User
	 */
	Optional<User> findByEmail(String email);
}
