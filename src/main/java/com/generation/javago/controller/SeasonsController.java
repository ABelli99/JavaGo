package com.generation.javago.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.generation.javago.model.repository.RoomBookingRepository;
import com.generation.javago.model.repository.RoomRepository;
import com.generation.javago.model.repository.SeasonsRepository;
import com.generation.javago.model.repository.User2Repository;


@CrossOrigin
@RestController
@RequestMapping

public class SeasonsController {
	
	@Autowired
	RoomBookingRepository rbrepo;
	
	@Autowired
	RoomRepository rrepo;
	
	@Autowired
	SeasonsRepository srepo;
	
	@Autowired
	User2Repository urepo;



}
