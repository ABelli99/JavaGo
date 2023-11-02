package com.generation.javago.model.dto.roombooking;

import java.time.LocalDate;

import com.generation.javago.model.entity.Room;
import com.generation.javago.model.entity.RoomBooking;
import com.generation.javago.model.entity.User;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
/**
 * Generic RoomBooking 
 * 
 * 		w/o objects,
 * 		closest to DB entity.
 * 
 * @author ABelli
 *
 */
public abstract class GenericRoomBookingDTO 
{

	public GenericRoomBookingDTO(RoomBooking rb) {
		id = rb.getId();
		check_in_date = rb.getCheckInDate();
		check_out_date = rb.getCheckOutDate();
		price = rb.getPrice();
		num_of_guests = rb.getNumOfGuests();
		guest_information = rb.getGuest_information();
	}
	
	public RoomBooking revertToRoomBooking() 
	{
		RoomBooking rb = RoomBooking.builder()
					 .id(id)
					 .checkInDate(check_in_date)
					 .checkOutDate(check_out_date)
					 .price(price)
					 .numOfGuests(num_of_guests)
					 .guest_information(guest_information)
				 .build();
		return rb;
	}
	
	
	protected Integer id;
	protected LocalDate check_in_date;
	protected LocalDate check_out_date;
	protected Double price;
	protected Integer num_of_guests;
	protected String guest_information;
}
