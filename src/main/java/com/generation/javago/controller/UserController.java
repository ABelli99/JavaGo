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

import com.generation.javago.controller.util.EmployeeUtil;
import com.generation.javago.controller.util.InvalidEntityException;
import com.generation.javago.controller.util.UnAuthorizedException;
import com.generation.javago.model.dto.roombooking.RoomBookingDTONoObj;
import com.generation.javago.model.dto.roombooking.RoomBookingDTONoUser;
import com.generation.javago.model.dto.user.UserDTO;
import com.generation.javago.model.dto.user.UserwBookingDTO;
import com.generation.javago.model.entity.RoomBooking;
import com.generation.javago.model.entity.User;
import com.generation.javago.model.repository.RoomBookingRepository;
import com.generation.javago.model.repository.RoomRepository;
import com.generation.javago.model.repository.SeasonsRepository;
import com.generation.javago.model.repository.User2Repository;

@RestController
@CrossOrigin
/**
 * Controller for Users entities
 * @author ABelli
 */
public class UserController {
    @Autowired
    User2Repository uRepo;
    
    @Autowired
    SeasonsRepository sRepo;
    
    @Autowired
    RoomRepository rRepo;
    
    @Autowired
    RoomBookingRepository rbRepo;
    
    @Autowired
    EmployeeUtil checkEmployee;
    
	/**
	 * send users + booked rooms of the user
	 * @return
	 * list with all users
	 */
	@GetMapping("/users")
	public List<UserwBookingDTO> getAll() 
	{
		if(!checkEmployee.isEmployee())
        	throw new UnAuthorizedException("Non sei un employee");
		
		return uRepo.findAll().stream().map(user-> new UserwBookingDTO(user)).toList();
	}
	
	
	/**
	 * send id from the user  email
	 * @return
	 * list with all users
	 */
	@PostMapping("/authenticate/{email}")
	public int userGetId(@PathVariable String email) {
		
		//check if tokenID is equals to the sent ID // ignored if employee
		if(!checkEmployee.isEmployee())
			if(!checkEmployee.getEmailFromToken().equalsIgnoreCase(email))
				throw new UnAuthorizedException("So cosa stai cercando di fare, brutto pagliaccio");
		
		if(uRepo.findByEmail(email).isEmpty())
			throw new NoSuchElementException("sei sotto effetto di droghe fra");
		
		return uRepo.findByEmail(email).get().getId();
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
		//check if tokenID is equals to the sent ID // ignored if employee
		if(!checkEmployee.isEmployee())
			if(checkEmployee.getIdFromToken()!=id)
				throw new UnAuthorizedException("So cosa stai cercando di fare, brutto pagliaccio");
		
		if(uRepo.findById(id).isEmpty())
			throw new NoSuchElementException("sei sotto effetto di droghe fra");
		
		return new UserwBookingDTO(uRepo.findById(id).get());
	}
	
	/**
	 *  >>UserDTO
	 * 		checks on the user
	 * 	<<Row in DB
	 * @param 		UserDTO
	 * @return 		row in DB
	 * @exception 	406
	 */
	@PostMapping("/addGuest")
	public UserDTO addGuest(@RequestBody UserDTO dto) 
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
	 * @param 		UserDTO
	 * @return 		row in DB
	 * @exception 	406
	 */
	@PostMapping("/addEmployee")
	public UserDTO addEmployee(@RequestBody UserDTO dto) 
	{
		if(!checkEmployee.isEmployee())
        	throw new UnAuthorizedException("Non sei un employee");
		
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
	
	/**
	 * >>UserDTO
	 * 		usual integrity checks
	 * <<RoomBooking of the user
	 * @param 		UserDTO
	 * @return 		RoomBookingDTO
	 */
	@GetMapping("/user/{id}/bookedrooms")
	public List<RoomBookingDTONoUser> getBookedRooms(@PathVariable int id) {
		
		//check if tokenID is equals to the sent ID // ignored if employee
		if(!checkEmployee.isEmployee())
			if(checkEmployee.getIdFromToken()!=id)
				throw new UnAuthorizedException("So cosa stai cercando di fare, brutto pagliaccio");
		
		//check if exist
		if(uRepo.findById(id).isEmpty())
			throw new NoSuchElementException("sei sotto effetto di droghe fra");
		
		//get single user with bookings
		UserwBookingDTO fanculo = new UserwBookingDTO(uRepo.findById(id).get());
		
		//get bookings from the user
		List<RoomBookingDTONoUser> res = fanculo.getBookings();
		return res;
	}
	
	/**
	 * >>Json RoomBookingNoObj
	 * 		usual integrity checks
	 * <<Row in DB RoomBooking
	 * @param 		Json
	 * @return 		RoomBookingDTONoUser // row in DB
	 * @exception 	406
	 * @exception 	401
	 */
	@PostMapping("/user/{userid}/bookaroom/{roomid}")
	public RoomBookingDTONoUser addBookedRoom(@PathVariable("userid") int userid, @PathVariable("roomid") int roomid, @RequestBody RoomBookingDTONoObj dto){
		//check if tokenID is equals to the sent ID // ignored if employee
		if(!checkEmployee.isEmployee())
			if(checkEmployee.getIdFromToken()!=userid)
				throw new UnAuthorizedException("So cosa stai cercando di fare, brutto pagliaccio");
		
		//check if user exist
		if(uRepo.findById(userid).isEmpty())
			throw new NoSuchElementException("sei sotto effetto di droghe fra, utente non trovato");	
		
		//check if room exist
		if(rRepo.findById(roomid).isEmpty())
			throw new NoSuchElementException("sei sotto effetto di droghe fra, room non trovata");	
		
		//getting roombooking
		RoomBooking toInsert = dto.revertToRoomBooking();
		
		//setting user to roombooking
		toInsert.setUser(uRepo.findById(userid).get());
		
		//setting room to roombooking
		toInsert.setRoom(rRepo.findById(roomid).get());
		
		return new RoomBookingDTONoUser(rbRepo.save(toInsert));
	}
	
	
}
