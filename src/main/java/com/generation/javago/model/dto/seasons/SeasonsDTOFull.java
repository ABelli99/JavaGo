package com.generation.javago.model.dto.seasons;

import com.generation.javago.model.entity.Seasons;

public class SeasonsDTOFull extends GenericSeasonsDTO {
	

protected Integer id;
protected String address;
protected localDate start_date , end_date;
protected Double percentage;

public GenericSeasonsDTO (Seasons seasons)
{
    id = season.getId();
    address = house.getAddress();
    address = house.getAddress();
    start_date = house.getStart_date();
    end_date = house.get_Enddate();
}


public Seasons convertToseasons()
{
	Seasons seasons = new Seasons();
	 Seasons season = new Season();
	    Seasons.setId(id);
	    Seasons.setAdress(adress);
	    Seasons.setstart_date(start_date);
	    Seasons.endstart_date(end_date);

    return house;
}