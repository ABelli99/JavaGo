package com.generation.javago.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import com.generation.javago.model.entity.RoomBooking;
import com.generation.javago.model.repository.UserRepository;

@RestController
@CrossOrigin
public class UserController {
	@Autowired
	RoomBooking repo;
	@Autowired
	UserRepository oRepo;
	
	
	
}
