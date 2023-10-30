package com.generation.javago.controller;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.generation.javago.model.dto.roombooking.RoomBookingDTOFull;
import com.generation.javago.model.repository.RoomBookingRepository;
import com.generation.javago.model.repository.RoomRepository;

/**
 * Controller per gestire le operazioni relative alle prenotazioni delle stanze.
 */
@CrossOrigin
@RestController
public class RoomBookingController {

    @Autowired
    RoomBookingRepository rbRepo;
    
    @Autowired
    RoomRepository rRepo;
	  
    @GetMapping("/bookings")
    public List<RoomBookingDTOFull> getAll() 
    {
        return rbRepo.findAll().stream().map(rb-> new RoomBookingDTOFull(rb)).toList();
    }
    
    @GetMapping("/bookings/{name}/roomname")
    public List<RoomBookingDTOFull> getBookingsByRoom(@PathVariable String name) 
    {
    	
        if(rRepo.findByRoomName(name)==null)
        	throw new NoSuchElementException("Non ho trovato alcuna prenotazione su quella stanza");
    	
        
        return rbRepo.findByRoom(rRepo.findByRoomName(name)).stream().map(room -> new RoomBookingDTOFull(room)).toList();
        
    }
  


}
