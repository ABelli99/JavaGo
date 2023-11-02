package com.generation.javago.controller.util;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.generation.javago.auth.config.JwtTokenUtil;
import com.generation.javago.model.repository.User2Repository;

/**
 * class for checking integrity on logged user
 * @author ABelli
 *
 */
@Component
public class EmployeeUtil 
{
	@Autowired
	User2Repository uRepo;
	@Autowired
	HttpServletRequest request;
	@Autowired
	JwtTokenUtil util;
	
	/**
	 * Check if the auth is an employee
	 * @return 
	 * 		true if is employee
	 * 		false if is not
	 */
	public boolean isEmployee() 
	{
		String token = request.getHeader("Authorization").substring(7);
		String email = util.getUsernameFromToken(token);
		return uRepo.findByEmail(email).getType().equals("employee");
	}
	
	public int getIdFromToken() {
		String token = request.getHeader("Authorization").substring(7);
		String email = util.getUsernameFromToken(token);
		
		return uRepo.findByEmail(email).getId();
	}

}
