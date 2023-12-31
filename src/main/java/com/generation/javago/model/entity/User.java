package com.generation.javago.model.entity;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * User is the single row of the DB.
 * 
 * Actual Entity of the DB in java
 * 
 * @author ABelli
 *
 */

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User implements Serializable
{

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String email;
	private String type;
	
	@JsonIgnore
	@OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
	private List<RoomBooking> booking;
	
	public boolean isValid()
    {
        return getErrors().isEmpty();
    }
	
    public List<String> getErrors() 
    {
        List<String> res= new ArrayList<String>();
        
        //check for pssw
        if(!patternMatches())
            res.add("email not valid\n");
        
        //check for type empty
        if(type.isBlank() || type==null)
            res.add("type not valid\n");
        
        //check for correct type
        if(!(isEmployee() || isGuest()))
        	res.add("type not valid");
        
        return res;
    }
    
    public boolean isEmployee() {
    	return type.equalsIgnoreCase("employee");
    }
    
    public boolean isGuest() {
    	return type.equalsIgnoreCase("guest");
    }

	public  boolean patternMatches() 
	{
	    return Pattern.compile( "^(.+)@(\\S+)$")
	      .matcher(email)
	      .matches();
	}
}
