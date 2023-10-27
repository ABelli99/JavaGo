package com.generation.javago.model.dto.room;

import com.generation.javago.model.entity.Room;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public abstract class GenericRoomDTO 
{

	private Integer id;
	private String room_name,notes;
	private Double base_price;
	private Integer capacity;
	
	public GenericRoomDTO () {}
	
	public GenericRoomDTO (Room room)
	{
		id = room.getId();
		room_name = room.getRoom_name();
		notes = room.getNotes();
		base_price = room.getBase_price();
		capacity = room.getCapacity();
	}
	
	public Room convertToRoom()
	{
		Room room = new Room();
		room.setId(id);
		room.setRoom_name(room_name);
		room.setNotes(notes);
		room.setBase_price(base_price);
		room.setCapacity(capacity);
		return room;
	}
	
	
}