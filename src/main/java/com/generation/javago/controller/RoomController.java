package com.generation.javago.controller;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.generation.javago.controller.util.EmployeeUtil;
import com.generation.javago.controller.util.UnAuthorizedException;
import com.generation.javago.model.dto.room.RoomDTOFull;
import com.generation.javago.model.dto.room.RoomDTONoList;
import com.generation.javago.model.dto.roombooking.RoomBookingDTONoRoom;
import com.generation.javago.model.dto.roombooking.RoomBookingDTONoUser;
import com.generation.javago.model.dto.user.UserwBookingDTO;
import com.generation.javago.model.entity.Room;
import com.generation.javago.model.repository.RoomBookingRepository;
import com.generation.javago.model.repository.RoomRepository;
import com.generation.javago.model.repository.SeasonsRepository;
import com.generation.javago.model.repository.User2Repository;

@CrossOrigin
@RestController
/**
 * Controller for Rooms entities
 * @author Gory, ABelli
 */
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
    
    @Autowired
    EmployeeUtil checkEmployee;

    @GetMapping("/rooms")
    public List<RoomDTONoList> getAll()
    {
        return rRepo.findAll()
        		.stream()
        		.map(room -> new RoomDTONoList(room)).toList();
    }

    @GetMapping("/room/{id}")
    public RoomDTONoList getOneRoomById(@PathVariable Integer id)
    {
    	Optional<Room> r= rRepo.findById(id);
    	if(r.isEmpty())
			throw new NoSuchElementException("Non ho trovato alcuna stanza con quell'id");

    	return new RoomDTONoList(r.get());
    }
    
    @GetMapping("/room/{name}/name")
    public RoomDTONoList getOneRoomByName(@PathVariable String name)
    {
    		if(rRepo.findByRoomName(name)==null)
    			throw new NoSuchElementException("Non ho trovato alcuna stanza con quel nome");

    	return new RoomDTONoList(rRepo.findByRoomName(name));
    }
    
    
    @GetMapping("/rooms/{nm}/capacity")
    public List<RoomDTONoList> getRoomByCapacity(@PathVariable Integer nm)
    {
		if(rRepo.findByCapacity(nm)==null)
			throw new NoSuchElementException("Non ho trovato alcuna stanza con quella capacità");
		
		return rRepo.findByCapacity(nm).stream().map(room -> new RoomDTONoList(room)).toList();
    }
    
    /**
	 * >>RoomDTO
	 * 		usual integrity checks
	 * <<RoomBooking of the Room
	 * @param 		RoomDTO
	 * @return 		RoomBookingDTONoRoom
	 */
	@GetMapping("/room/{id}/bookedrooms")
	public List<RoomBookingDTONoRoom> getBookedRooms(@PathVariable int id) {
		
		//check if is Employee
		if(!checkEmployee.isEmployee())
        	throw new UnAuthorizedException("Non sei un employee");
		
		//check if exist
		if(rRepo.findById(id).isEmpty())
			throw new NoSuchElementException("Non ho trovato alcuna stanza con quell'id");
		
		//get single user with bookings
		RoomDTOFull fk = new RoomDTOFull(rRepo.findById(id).get());
		
		//get bookings from the user
		List<RoomBookingDTONoRoom> res = fk.getBooking();
		return res;
	}
    

}