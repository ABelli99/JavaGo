package com.generation.javago.model.entity;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Bookings of the rooms, 
 * 		have User & 
 * 			 Room obj
 * 
 * Actual Entity of the DB in java
 * 
 * @author ABelli
 *
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class RoomBooking {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="room",referencedColumnName = "roomName")
	private Room room;
	private LocalDate checkInDate;
	private LocalDate checkOutDate;
	private Double price;
	private Integer numOfGuests;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="email",referencedColumnName = "email")
	private User user;
	
	private String guest_information;
	
	public boolean isBetween(LocalDate data1,LocalDate data2 ) 
	{      
		boolean isData1Between = data1.isAfter(checkInDate) && data1.isBefore(checkOutDate);
	    boolean isData2Between = data2.isAfter(checkInDate) && data2.isBefore(checkOutDate);
	    return isData1Between || isData2Between;  
	}
	
}
