package com.generation.javago;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest	(
	  	webEnvironment = SpringBootTest.WebEnvironment.MOCK,
	  	classes = JavagoApplicationNew.class
		)

/**
 * test for roombookingcontroller
 * @author ABelli
 *
 */

@AutoConfigureMockMvc
public class RoomBookingControllerTest {

	String tokenEmployee = "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZXN0IiwiZXhwIjoyMDU4NzczNTI5LCJpYXQiOjE2OTg3NzM1Mjl9.Ehqi0E5KjhCWQsDy4xb5aR-deiatxTFH2Gnn8CJhWc0OHk-9OUPgKh4WapaKVEYobwBMKxEMIdz0E-rRDq_h5Q";
	String tokenGuest = "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZXN0R3Vlc3QiLCJleHAiOjIwNTg5MjU5NjQsImlhdCI6MTY5ODkyNTk2NH0.cByoO363-tIs87NlnflDcWb2z4DV3awUUxJyA7Byk1RDQUsChNC18C9DbtPUbm_KcXCqGyjtG0XlNJWy2vETGg";
	
	@Autowired
	MockMvc mock;

	@Test
	void testGetAllBookings() throws Exception{
		/**
		 * Check if all the bookings in bookings is 
		 * equals to the json of the all bookings got from postman
		 * as employee
		 * @test
		 */
		mock.perform(MockMvcRequestBuilders.get("/bookings").header("Authorization", tokenEmployee))
			.andDo(print())											
			.andExpect(status()
				.isOk())								
			.andExpect(MockMvcResultMatchers.content()
				.json(bookingsjson));
		/**
		 * Check if the result is
		 * error code Unautorized (401)
		 * && text "Non sei un employee"
		 * as guest
		 * @test
		 */
		mock.perform(MockMvcRequestBuilders.get("/bookings").header("Authorization", tokenGuest))
		.andDo(print())											
		.andExpect(status()
				.isUnauthorized())								
		.andExpect(MockMvcResultMatchers.content()
				.string("Non sei un employee"));
	}
	
	@Test
	void testGetBookingsByRoom() throws Exception{
		/**
		 * Check if all the bookings in the room a is 
		 * equals to the json of the all bookings in room a got from postman
		 * as employee
		 * @test
		 */
		mock.perform(MockMvcRequestBuilders.get("/bookings/a/roomname").header("Authorization", tokenEmployee))
			.andDo(print())											
			.andExpect(status()
				.isOk())								
			.andExpect(MockMvcResultMatchers.content()
				.json(bookingsajson));
		/**
		 * Check if the result is
		 * error code Unautorized (401)
		 * && text "Non sei un employee"
		 * as guest
		 * @test
		 */
		mock.perform(MockMvcRequestBuilders.get("/bookings/a/roomname").header("Authorization", tokenGuest))
		.andDo(print())											
		.andExpect(status()
				.isUnauthorized())								
		.andExpect(MockMvcResultMatchers.content()
				.string("Non sei un employee"));
	}
	
	@Test
	void testGetBookingsBetween() throws Exception{
		
		 String date1 = "28-10-2023";
		 String date2 = "30-10-2023";
		    
		 String urlDate = "/bookings/"+date1+"/"+date2;
		 
		 /**
		 * Check if all the bookings in a timezone is 
		 * ok
		 * as employee
		 * @test
		 */
		 mock.perform(MockMvcRequestBuilders
			 .post(urlDate).header("Authorization", tokenEmployee)
			 .contentType(MediaType.APPLICATION_JSON))
		     .andExpect(MockMvcResultMatchers.status()
		    		 .isOk());
		/**
		 * Check if the result is
		 * error code Unautorized (401)
		 * && text "Non sei un employee"
		 * as guest
		 * @test
		 */
		 mock.perform(MockMvcRequestBuilders
			 .post(urlDate).header("Authorization", tokenGuest)
			 .contentType(MediaType.APPLICATION_JSON))
			 .andExpect(MockMvcResultMatchers.status()
					 .isUnauthorized())
			 .andExpect(MockMvcResultMatchers.content()
					 .string("Non sei un employee"));
	}
	
	String bookingsjson = "[\r\n"
			+ "    {\r\n"
			+ "        \"id\": 1,\r\n"
			+ "        \"check_in_date\": \"2023-10-28\",\r\n"
			+ "        \"check_out_date\": \"2023-10-30\",\r\n"
			+ "        \"price\": 50.0,\r\n"
			+ "        \"num_of_guests\": 1,\r\n"
			+ "        \"guest_information\": \"early check-in requested\",\r\n"
			+ "        \"room\": {\r\n"
			+ "            \"id\": 1,\r\n"
			+ "            \"roomName\": \"a\",\r\n"
			+ "            \"notes\": \"\",\r\n"
			+ "            \"base_price\": 50.0,\r\n"
			+ "            \"capacity\": 1\r\n"
			+ "        },\r\n"
			+ "        \"user\": {\r\n"
			+ "            \"id\": 1,\r\n"
			+ "            \"email\": \"vipguest@gmail.com\",\r\n"
			+ "            \"type\": \"guest\",\r\n"
			+ "            \"valid\": true,\r\n"
			+ "            \"errors\": [],\r\n"
			+ "            \"employee\": false,\r\n"
			+ "            \"guest\": true\r\n"
			+ "        }\r\n"
			+ "    }\r\n"
			+ "]";
	String bookingsajson = "[\r\n"
			+ "    {\r\n"
			+ "        \"id\": 1,\r\n"
			+ "        \"check_in_date\": \"2023-10-28\",\r\n"
			+ "        \"check_out_date\": \"2023-10-30\",\r\n"
			+ "        \"price\": 50.0,\r\n"
			+ "        \"num_of_guests\": 1,\r\n"
			+ "        \"guest_information\": \"early check-in requested\",\r\n"
			+ "        \"room\": {\r\n"
			+ "            \"id\": 1,\r\n"
			+ "            \"roomName\": \"a\",\r\n"
			+ "            \"notes\": \"\",\r\n"
			+ "            \"base_price\": 50.0,\r\n"
			+ "            \"capacity\": 1\r\n"
			+ "        },\r\n"
			+ "        \"user\": {\r\n"
			+ "            \"id\": 1,\r\n"
			+ "            \"email\": \"vipguest@gmail.com\",\r\n"
			+ "            \"type\": \"guest\",\r\n"
			+ "            \"valid\": true,\r\n"
			+ "            \"errors\": [],\r\n"
			+ "            \"employee\": false,\r\n"
			+ "            \"guest\": true\r\n"
			+ "        }\r\n"
			+ "    }\r\n"
			+ "]";

}
