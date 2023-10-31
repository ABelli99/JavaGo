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
public abstract class GenericRoomBookingDTO 
{

	public GenericRoomBookingDTO(RoomBooking rb) {
		id = rb.getId();
		check_in_date = rb.getCheck_in_date();
		check_out_date = rb.getCheck_out_date();
		price = rb.getPrice();
		num_of_guests = rb.getNum_of_guests();
		guest_information = rb.getGuest_information();
	}
	
	public RoomBooking revertToRoomBooking() 
	{
		RoomBooking rb = RoomBooking.builder()
					 .id(id)
					 .check_in_date(check_in_date)
					 .check_out_date(check_out_date)
					 .price(price)
					 .num_of_guests(num_of_guests)
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
