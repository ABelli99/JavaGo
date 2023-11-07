package com.generation.javago.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.generation.javago.controller.util.EmployeeUtil;
import com.generation.javago.controller.util.UnAuthorizedException;
import com.generation.javago.model.dto.roombooking.RoomBookingDTOFull;
import com.generation.javago.model.entity.Season;
import com.generation.javago.model.repository.RoomBookingRepository;
import com.generation.javago.model.repository.RoomRepository;
import com.generation.javago.model.repository.SeasonsRepository;

@CrossOrigin
@RestController
/**
 * Controller for Bookings of the rooms
 * @author ABelli
 */
public class RoomBookingController {
	
    @Autowired
    RoomBookingRepository rbRepo;
    
    @Autowired
    RoomRepository rRepo;
    
    @Autowired
    SeasonsRepository sRepo;
    
    @Autowired
    EmployeeUtil checkEmployee;
	 
	/**
	 * >>no entry
	 * 		usual integrity checks
	 * <<List of booking
	 * @param 		Json
	 * @return 		List of booking
	 */
    @GetMapping("/bookings")
    public List<RoomBookingDTOFull> getAll() 
    {
    	
    	if(!checkEmployee.isEmployee())
        	throw new UnAuthorizedException("Non sei un employee");
    	
        return rbRepo.findAll().stream().map(rb-> new RoomBookingDTOFull(rb)).toList();
    }
    
    @GetMapping("/bookings/{name}/roomname")
    public List<RoomBookingDTOFull> getBookingsByRoom(@PathVariable String name) 
    {
    	
    	if(!checkEmployee.isEmployee())
        	throw new UnAuthorizedException("Non sei un employee");
    	
        if(rRepo.findByRoomName(name)==null)
        	throw new NoSuchElementException("Non ho trovato alcuna prenotazione su quella stanza");
    	
        
        return rbRepo.findByRoom(rRepo.findByRoomName(name)).stream().map(room -> new RoomBookingDTOFull(room)).toList();
        
    }
  
    @PostMapping("/bookings/{date1}/{date2}")
    public List<RoomBookingDTOFull> getBookingsBetween(@PathVariable("date1") String date1, @PathVariable("date2") String date2){

    	
    	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    	LocalDate realDate1 = LocalDate.parse(date1,formatter), realDate2 = LocalDate.parse(date2,formatter);
    	
    	
    	List<RoomBookingDTOFull> res = rbRepo.findByCheckInDateCheckOutDate(realDate1, realDate2)
    										 .stream().map(
    												 		room -> 
    												 		new RoomBookingDTOFull(room)
    												 	  )
    										 .toList();
    	return res;
    }
    
    /**
     * 
     * @param userid
     * @param date1
     * @param date2
     * @return multiplier
     */
    @PostMapping("/overprice/{date1}/{date2}")
	public Double findOverprice
	(
		@PathVariable("date1") String date1, 
		@PathVariable("date2") String date2
	){		
		//formatting dates
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    	LocalDate realDate1 = LocalDate.parse(date1,formatter), realDate2 = LocalDate.parse(date2,formatter);
		
		//get seasons within the book
		List<Season> monneys = sRepo.findByStartAndEndDate(realDate1,realDate2);
		
		//calc raw multiplier
		double mol = 1;
		if(!monneys.isEmpty())
			for(Season s : monneys) {
				mol += s.getPercentage()/100;
			}
		
		return mol;
	}
	
    

    /* not used
    @PostMapping("/bookings/{capacity}")
    public List<RoomBookingDTOFull> getBookingsBetween(@PathVariable("capacity") int capacity){
    	
    	List<RoomBookingDTOFull> res = rbRepo.findByNumOfGuests(capacity)
    										 .stream().map(
    												 		room -> 
    												 		new RoomBookingDTOFull(room)
    												 	  )
    										 .toList();
    	return res;
    }
	*/
}
