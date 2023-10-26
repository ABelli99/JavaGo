package com.generation.javago.model.dto.seasons;

import com.generation.javago.model.entity.Seasons;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class GenericSeasonsDTO {

	protected Integer id;

	protected String season;
	protected String address;
	protected localDate start_date , end_date;
	protected Double percentage;
	
	
}

public GenericHouseDTO (House house)
{
    id = season.getId();
    address = house.getAddress();
    start_date = house.getStart_date();
    end_date = house.get_Enddate();
}


public Season convertToSeason()
{
    Seasons season = new Season();
    Seasons.setId(id);
    Seasons.setAdress(adress);
    Seasons.setstart_date(start_date);
    Seasons.endstart_date(end_date);


    return season;
}
