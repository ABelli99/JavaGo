package com.generation.javago;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest	(
	  	webEnvironment = SpringBootTest.WebEnvironment.MOCK,
	  	classes = JavagoApplicationNew.class
		)

/**
 * tests for room controller
 * @author ABelli
 *
 */

@AutoConfigureMockMvc
class RoomControllerTest {

	String tokenEmployee = "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZXN0IiwiZXhwIjoyMDU4NzczNTI5LCJpYXQiOjE2OTg3NzM1Mjl9.Ehqi0E5KjhCWQsDy4xb5aR-deiatxTFH2Gnn8CJhWc0OHk-9OUPgKh4WapaKVEYobwBMKxEMIdz0E-rRDq_h5Q";
	String tokenGuest = "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZXN0R3Vlc3QiLCJleHAiOjIwNTg5MjU5NjQsImlhdCI6MTY5ODkyNTk2NH0.cByoO363-tIs87NlnflDcWb2z4DV3awUUxJyA7Byk1RDQUsChNC18C9DbtPUbm_KcXCqGyjtG0XlNJWy2vETGg";
	
	@Autowired
	MockMvc mock;
	
	@Test
	void testGetAllRooms() throws Exception{
		/**
		 * Check if all the rooms in rooms is 
		 * equals to the json of the all rooms got from postman
		 * @test
		 */
		mock.perform(MockMvcRequestBuilders.get("/rooms").header("Authorization", tokenEmployee))
			.andDo(print())											
			.andExpect(status()
				.isOk())								
			.andExpect(MockMvcResultMatchers.content()
				.json(roomsjson));
	}
	
	@Test
	void testGetOneRoomById() throws Exception
	{
		/**
		 * Check if the first element in rooms is 
		 * equals to the json of the first user got from postman
		 * @test
		 */
		mock.perform(MockMvcRequestBuilders.get("/room/1").header("Authorization", tokenEmployee))  
			.andDo(print())											
			.andExpect(status()
				.isOk())								
			.andExpect(MockMvcResultMatchers.content()
				.json(room1json));	
		
		/**
		 * Check if the invalid link in rooms is 
		 * equals to the error code bad request 
		 * && gives the string "Controlla se stai inviando il parametro corretto"
		 * @test
		 */
		mock.perform(MockMvcRequestBuilders.get("/room/paperino").header("Authorization", tokenEmployee))  
			.andDo(print())											
			.andExpect(status()
				.isBadRequest())								
			.andExpect(MockMvcResultMatchers.content()
				.string("Controlla se stai inviando il parametro corretto"));
			
		/**
		 * Check if the invalid element in rooms is 
		 * equals to the error code not found
		 * && gives the string "Non ho trovato alcuna stanza con quell'id"
		 * @test
		 */
		mock.perform(MockMvcRequestBuilders.get("/room/255").header("Authorization", tokenEmployee))  
			.andDo(print())											
			.andExpect(status()
				.isNotFound())								
			.andExpect(MockMvcResultMatchers.content()
				.string("Non ho trovato alcuna stanza con quell'id"));
		
		
	}
	
	@Test
	void testGetRoomByName() throws Exception{
		/**
		 * Check if the room w/ name "a" in rooms is 
		 * equals to the json of room "a" got from postman
		 * @test
		 */
		mock.perform(MockMvcRequestBuilders.get("/room/a/name").header("Authorization", tokenEmployee))  
			.andDo(print())											
			.andExpect(status()
				.isOk())								
			.andExpect(MockMvcResultMatchers.content()
				.json(room1json));	
		
		/**
		 * Check if the invalid element in rooms is 
		 * equals to the error code not found
		 * && gives the string "Non ho trovato alcuna stanza con quel nome"
		 * @test
		 */
		mock.perform(MockMvcRequestBuilders.get("/room/aaaaaaaaa/name").header("Authorization", tokenEmployee))  
		.andDo(print())											
		.andExpect(status()
				.isNotFound())								
		.andExpect(MockMvcResultMatchers.content()
				.string("Non ho trovato alcuna stanza con quel nome"));
		
		/**
		 * Check if the invalid element in rooms is 
		 * equals to the error code not found
		 * && gives the string "Non ho trovato alcuna stanza con quel nome"
		 * @test
		 */
		mock.perform(MockMvcRequestBuilders.get("/room/123456789/name").header("Authorization", tokenEmployee))  
		.andDo(print())											
		.andExpect(status()
				.isNotFound())								
		.andExpect(MockMvcResultMatchers.content()
				.string("Non ho trovato alcuna stanza con quel nome"));
	}

	@Test
	void testGetRoomByCapacity() throws Exception{
		/**
		 * Check if the room w/ capacity 2 in rooms is 
		 * equals to the json of rooms got from postman
		 * @test
		 */
		mock.perform(MockMvcRequestBuilders.get("/rooms/2/capacity").header("Authorization", tokenEmployee))  
			.andDo(print())											
			.andExpect(status()
				.isOk())								
			.andExpect(MockMvcResultMatchers.content()
				.json(roomscapacity2json));	
		
		/**
		 * Check if the invalid element in rooms is 
		 * equals to the error code bad request
		 * && gives the string "Controlla se stai inviando il parametro corretto"
		 * @test
		 */
		mock.perform(MockMvcRequestBuilders.get("/rooms/aaaaaaaaa/capacity").header("Authorization", tokenEmployee))  
		.andDo(print())											
		.andExpect(status()
				.isBadRequest())								
		.andExpect(MockMvcResultMatchers.content()
				.string("Controlla se stai inviando il parametro corretto"));
		
		/**
		 * Check if the invalid element in rooms is 
		 * an empty json 
		 * @test
		 */
		mock.perform(MockMvcRequestBuilders.get("/rooms/123456789/capacity").header("Authorization", tokenEmployee))  
		.andDo(print())											
		.andExpect(status()
				.isOk())								
		.andExpect(MockMvcResultMatchers.content()
				.json(noroomsjson));	
	}
	
	@Test
	void testGetBookedRooms() throws Exception{
		/**
		 * Check if the first element in rooms is 
		 * equals to the json of the first user got from postman
		 * @test
		 */
		mock.perform(MockMvcRequestBuilders.get("/room/1/bookedrooms").header("Authorization", tokenEmployee))  
			.andDo(print())											
			.andExpect(status()
				.isOk())								
			.andExpect(MockMvcResultMatchers.content()
				.json(room1booksjson));	
		/**
		 * Check if the first element in rooms is 
		 * equals to the json of the first user got from postman
		 * @test
		 */
		mock.perform(MockMvcRequestBuilders.get("/room/1/bookedrooms").header("Authorization", tokenGuest))  
			.andDo(print())											
			.andExpect(status()
				.isUnauthorized())								
			.andExpect(MockMvcResultMatchers.content()
				.string("Non sei un employee"));
		
		/**
		 * Check if the invalid link in rooms is 
		 * equals to the error code bad request 
		 * && gives the string "Controlla se stai inviando il parametro corretto"
		 * @test
		 */
		mock.perform(MockMvcRequestBuilders.get("/room/paperino/bookedrooms").header("Authorization", tokenEmployee))  
			.andDo(print())											
			.andExpect(status()
				.isBadRequest())								
			.andExpect(MockMvcResultMatchers.content()
				.string("Controlla se stai inviando il parametro corretto"));
			
	}

	@SuppressWarnings("unused")
	private static String _asJsonString(Object obj)
	{
	    try 
	    {
	        return new ObjectMapper().writeValueAsString(obj);
	    } 
	    catch (Exception e) 
	    {
	        throw new RuntimeException(e);
	    }
	}
	
	String room1json = "{\r\n"
			+ "    \"id\": 1,\r\n"
			+ "    \"room_name\": \"a\",\r\n"
			+ "    \"notes\": \"\",\r\n"
			+ "    \"base_price\": 50.0,\r\n"
			+ "    \"capacity\": 1\r\n"
			+ "}";
	
	String roomsjson = "[\r\n"
			+ "    {\r\n"
			+ "        \"id\": 1,\r\n"
			+ "        \"room_name\": \"a\",\r\n"
			+ "        \"notes\": \"\",\r\n"
			+ "        \"base_price\": 50.0,\r\n"
			+ "        \"capacity\": 1\r\n"
			+ "    },\r\n"
			+ "    {\r\n"
			+ "        \"id\": 2,\r\n"
			+ "        \"room_name\": \"b\",\r\n"
			+ "        \"notes\": \"\",\r\n"
			+ "        \"base_price\": 80.0,\r\n"
			+ "        \"capacity\": 1\r\n"
			+ "    },\r\n"
			+ "    {\r\n"
			+ "        \"id\": 3,\r\n"
			+ "        \"room_name\": \"c\",\r\n"
			+ "        \"notes\": \"\",\r\n"
			+ "        \"base_price\": 90.0,\r\n"
			+ "        \"capacity\": 2\r\n"
			+ "    },\r\n"
			+ "    {\r\n"
			+ "        \"id\": 4,\r\n"
			+ "        \"room_name\": \"d\",\r\n"
			+ "        \"notes\": \"\",\r\n"
			+ "        \"base_price\": 110.0,\r\n"
			+ "        \"capacity\": 2\r\n"
			+ "    },\r\n"
			+ "    {\r\n"
			+ "        \"id\": 5,\r\n"
			+ "        \"room_name\": \"e\",\r\n"
			+ "        \"notes\": \"\",\r\n"
			+ "        \"base_price\": 150.0,\r\n"
			+ "        \"capacity\": 2\r\n"
			+ "    },\r\n"
			+ "    {\r\n"
			+ "        \"id\": 6,\r\n"
			+ "        \"room_name\": \"f\",\r\n"
			+ "        \"notes\": \"\",\r\n"
			+ "        \"base_price\": 115.0,\r\n"
			+ "        \"capacity\": 3\r\n"
			+ "    }\r\n"
			+ "]";

	String roomscapacity2json = "[\r\n"
			+ "    {\r\n"
			+ "        \"id\": 3,\r\n"
			+ "        \"room_name\": \"c\",\r\n"
			+ "        \"notes\": \"\",\r\n"
			+ "        \"base_price\": 90.0,\r\n"
			+ "        \"capacity\": 2\r\n"
			+ "    },\r\n"
			+ "    {\r\n"
			+ "        \"id\": 4,\r\n"
			+ "        \"room_name\": \"d\",\r\n"
			+ "        \"notes\": \"\",\r\n"
			+ "        \"base_price\": 110.0,\r\n"
			+ "        \"capacity\": 2\r\n"
			+ "    },\r\n"
			+ "    {\r\n"
			+ "        \"id\": 5,\r\n"
			+ "        \"room_name\": \"e\",\r\n"
			+ "        \"notes\": \"\",\r\n"
			+ "        \"base_price\": 150.0,\r\n"
			+ "        \"capacity\": 2\r\n"
			+ "    }\r\n"
			+ "]";

	String noroomsjson = "[]";
	
	String room1booksjson = "[\r\n"
			+ "    {\r\n"
			+ "        \"id\": 1,\r\n"
			+ "        \"check_in_date\": \"2023-10-28\",\r\n"
			+ "        \"check_out_date\": \"2023-10-30\",\r\n"
			+ "        \"price\": 50.0,\r\n"
			+ "        \"num_of_guests\": 1,\r\n"
			+ "        \"guest_information\": \"early check-in requested\",\r\n"
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
