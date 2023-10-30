package com.generation.javago.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.generation.javago.model.dto.room.RoomDTONoList;
import com.generation.javago.model.dto.roombooking.RoomBookingDTOFull;
import com.generation.javago.model.entity.RoomBooking;
import com.generation.javago.model.repository.RoomBookingRepository;

/**
 * Controller per gestire le operazioni relative alle prenotazioni delle stanze.
 */
@CrossOrigin
@RestController
public class RoomBookingController {

    @Autowired
    RoomBookingRepository rbRepo;
	  
    @GetMapping("/bookings")
    public List<RoomBookingDTOFull> getAll() 
    {
        return rbRepo.findAll().stream().map(rb-> new RoomBookingDTOFull(rb)).toList();
    }
    
    @GetMapping("/bookings/{name}/roomname")
    public List<RoomBookingDTOFull> getBookingsByRoom(@PathVariable String name) 
    {
        if(rbRepo.findByRoom(name)==null)
        	throw new NoSuchElementException("Non ho trovato alcuna prenotazione su quella stanza");
    	
        return rbRepo.findByRoom(name).stream().map(room -> new RoomBookingDTOFull(room)).toList();
        
    }
  


}
