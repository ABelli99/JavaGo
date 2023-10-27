package com.generation.javago.model.dto.seasons;

import java.time.LocalDate;

import com.generation.javago.model.entity.Season;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor

public  abstract class GenericSeasonDTO {

	protected Integer id;
	protected String season;
	protected LocalDate start_date , end_date;
	protected Double percentage;
	
	public GenericSeasonDTO () {}


public GenericSeasonDTO (Season seasons)
{
	    id = seasons.getId();
	    season = seasons.getSeason();
	    start_date = seasons.getStart_date();
	    end_date = seasons.getEnd_date();
	    percentage= seasons.getPercentage();
}


public Season convertToSeason()
{
    Season seasons = new Season(); //dichiarazione s 
    seasons.setId(id);               //istanzio s 
    seasons.setSeason(season);
    seasons.setStart_date(start_date);
    seasons.setEnd_date(end_date);
    seasons.setPercentage(percentage);
//sus
    return seasons;
}
}