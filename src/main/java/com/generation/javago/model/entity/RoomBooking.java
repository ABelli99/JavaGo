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
	@JoinColumn(name="room")
	private Room room;
	private LocalDate check_in_date;
	private LocalDate check_out_date;
	private Double price;
	private Integer num_of_guests;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="email")
	private User user;
	
	private String guest_information;
	
	public boolean isBetween(LocalDate data1,LocalDate data2 ) 
	{      
		boolean isData1Between = data1.isAfter(check_in_date) && data1.isBefore(check_out_date);
	    boolean isData2Between = data2.isAfter(check_in_date) && data2.isBefore(check_out_date);
	    return isData1Between || isData2Between;  
	}
	
}
