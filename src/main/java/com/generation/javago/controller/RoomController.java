package com.generation.javago.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;

import com.generation.javago.model.repository.RoomBookingRepository;
import com.generation.javago.model.repository.RoomRepository;
import com.generation.javago.model.repository.SeasonsRepository;
import com.generation.javago.model.repository.UserRepository;

@CrossOrigin
@RestController
public class RoomController 
{
	
	@Autowired
	RoomBookingRepository rbrepo;
	@Autowired
	RoomRepository rrepo;
	@Autowired
	SeasonsRepository srepo;
	@Autowired
	UserRepository urepo;


	
}
