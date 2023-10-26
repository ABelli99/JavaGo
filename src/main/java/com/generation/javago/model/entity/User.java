package com.generation.javago.model.entity;


import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String email;
	private String type;
	
	
	@OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
	private List<RoomBooking> booking;
	
	public boolean isValid()
    {
        return getErrors().isEmpty();
    }
	
    public List<String> getErrors() 
    {
        List<String> res= new ArrayList<String>();
        
        if(!patternMatches())
            res.add("email not valid\n");
        
        if(type.isBlank() || type==null)
            res.add("type not valid\n");
        
        return res;
    }

	public  boolean patternMatches() 
	{
	    return Pattern.compile( "^(.+)@(\\S+)$")
	      .matcher(email)
	      .matches();
	}
}
