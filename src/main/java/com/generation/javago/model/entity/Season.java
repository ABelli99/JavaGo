package com.generation.javago.model.entity;


import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

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
public class Season 
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String season;
	private LocalDate start_date , end_date;
	private Double percentage;
	
	
	
	public boolean isSeason(LocalDate data1,LocalDate data2 ) 
	{      
		boolean isData1Between = data1.isAfter(start_date) && data1.isBefore(end_date);
	    boolean isData2Between = data2.isAfter(start_date) && data2.isBefore(end_date);
	    return isData1Between || isData2Between ;  
	}
	
	
	
}


