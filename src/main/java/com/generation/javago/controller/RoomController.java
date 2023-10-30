package com.generation.javago.controller;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.generation.javago.model.dto.room.RoomDTONoList;
import com.generation.javago.model.entity.Room;
import com.generation.javago.model.repository.RoomBookingRepository;
import com.generation.javago.model.repository.RoomRepository;
import com.generation.javago.model.repository.SeasonsRepository;
import com.generation.javago.model.repository.User2Repository;

@CrossOrigin
@RestController
public class RoomController 
{

    @Autowired
    RoomBookingRepository rbRepo;
    @Autowired
    RoomRepository rRepo;
    @Autowired
    SeasonsRepository sRepo;
    @Autowired
    User2Repository uRepo;

    @GetMapping("/rooms")
    public List<RoomDTONoList> getAll()
    {
        return rRepo.findAll().stream().map(room -> new RoomDTONoList(room)).toList();
    }

    @GetMapping("/rooms/{id}")
    public RoomDTONoList getOneRoomById(@PathVariable Integer id)
    {
    	Optional<Room> r= rRepo.findById(id);
    		if(r.isEmpty())
    			throw new NoSuchElementException("Non ho trovato alcuna stanza con quell'id");

    	return new RoomDTONoList(r.get());
    }
    
    @GetMapping("/rooms/{name}/name")
    public RoomDTONoList getOneRoomByName(@PathVariable String name)
    {
    		if(rRepo.findByRoomName(name)==null)
    			throw new NoSuchElementException("Non ho trovato alcuna stanza con quel nome");

    	return new RoomDTONoList(rRepo.findByRoomName(name));
    }
    
    

}