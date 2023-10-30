package com.generation.javago.controller;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.generation.javago.controller.util.InvalidEntityException;
import com.generation.javago.model.dto.user.UserDTO;
import com.generation.javago.model.dto.user.UserwBookingDTO;
import com.generation.javago.model.entity.User;
import com.generation.javago.model.repository.User2Repository;

@RestController
@CrossOrigin
public class UserController {
    @Autowired
    User2Repository uRepo;
	/**
	 * send users + booked rooms of the user
	 * @return
	 * list with all users
	 */
	@GetMapping("/users")
	public List<UserwBookingDTO> getAll() 
	{
		return uRepo.findAll().stream().map(user-> new UserwBookingDTO(user)).toList();
	}
	
	
	/**
	 * get int from URI, 
	 * 		if exist send user, 
	 * 		else send exception 
	 * @param 		id
	 * @return 		User
	 * @exception 	404
	 */
	@GetMapping("/user/{id}")
	public UserwBookingDTO getOne(@PathVariable int id) 
	{
		if(uRepo.findById(id).isEmpty())
			throw new NoSuchElementException("sei sotto effetto di droghe fra");
		return new UserwBookingDTO(uRepo.findById(id).get());
	}
	
	/**
	 *  >>UserDTO
	 * 		checks on the user
	 * 	<<Row in DB
	 * @param 		User
	 * @return 		row in DB
	 * @exception 	406
	 */
	@PostMapping("/addGuest")
	public UserDTO createOne(@RequestBody UserDTO dto) 
	{
		User toInsert = dto.revertToUser();
		
		//integrity of the user
		if(!toInsert.isValid())
			throw new InvalidEntityException("se provi a inserire un altra volta un utente invalido ti mando Samuele con il coltello sotto casa");
		//check for force bypass
		if(!toInsert.isGuest())
			throw new InvalidEntityException("Brutto coglione non ci provare a registrarti come employee");
		
		//success
		return new UserDTO(uRepo.save(toInsert));
	}
	
	/**
	 * >>UserDTO
	 * 		checks on the user
	 * <<Row in DB
	 * @param 		User
	 * @return 		row in DB
	 * @exception 	406
	 */
	@PostMapping("/addEmployee")
	public UserDTO addEmployee(@RequestBody UserDTO dto) 
	{
		User toInsert = dto.revertToUser();
		
		//check integrity of the user
		if(!toInsert.isValid())
			throw new InvalidEntityException("se provi a inserire un altra volta un cliente invalido ti mando Samuele con il coltello sotto casa");
		
		//check for force bypass from an employeeS
		if(!toInsert.isEmployee())
			throw new InvalidEntityException("Qualcosa é andato storto nella registrazione dell' employee");
		
		//success
		return new UserDTO(uRepo.save(toInsert));
	}
	
}
