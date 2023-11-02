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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.generation.javago.model.dto.user.UserDTO;

@SpringBootTest	(
	  	webEnvironment = SpringBootTest.WebEnvironment.MOCK,
	  	classes = JavagoApplicationNew.class
		)
/**
 * tests for user controllers
 * @author ABelli
 *
 */

@AutoConfigureMockMvc
class UserControllerTest {

	String tokenEmployee = "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZXN0IiwiZXhwIjoyMDU4NzczNTI5LCJpYXQiOjE2OTg3NzM1Mjl9.Ehqi0E5KjhCWQsDy4xb5aR-deiatxTFH2Gnn8CJhWc0OHk-9OUPgKh4WapaKVEYobwBMKxEMIdz0E-rRDq_h5Q";
	String tokenGuest = "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZXN0R3Vlc3QiLCJleHAiOjIwNTg5MjU5NjQsImlhdCI6MTY5ODkyNTk2NH0.cByoO363-tIs87NlnflDcWb2z4DV3awUUxJyA7Byk1RDQUsChNC18C9DbtPUbm_KcXCqGyjtG0XlNJWy2vETGg";
	@Autowired
	MockMvc mock;
	
	@Test
	void testGetOne() throws Exception
	{
		/**
		 * Check if the first element in user is 
		 * equals to the json of the first user got from postman
		 * @test
		 */
		mock.perform(MockMvcRequestBuilders.get("/user/1").header("Authorization", tokenEmployee))  
		.andDo(print())											
		.andExpect(status()
			.isOk())								
		.andExpect(MockMvcResultMatchers.content()
			.json(userjson));	
		
		/**
		 * Check if the invalid link in user is 
		 * equals to the error code bad request 
		 * && gives the string "Controlla se stai inviando il parametro corretto"
		 * @test
		 */
		mock.perform(MockMvcRequestBuilders.get("/user/paperino").header("Authorization", tokenEmployee))  
		.andDo(print())											
		.andExpect(status()
			.isBadRequest())								
		.andExpect(MockMvcResultMatchers.content()
			.string("Controlla se stai inviando il parametro corretto"));
		
		/**
		 * Check if the invalid element in user is 
		 * equals to the error code not found
		 * && gives the string "sei sotto effetto di droghe fra"
		 * @test
		 */
		mock.perform(MockMvcRequestBuilders.get("/user/255").header("Authorization", tokenEmployee))  
		.andDo(print())											
		.andExpect(status()
			.isNotFound())								
		.andExpect(MockMvcResultMatchers.content()
			.string("sei sotto effetto di droghe fra"));
	}
	
	@Test
	void testAddGuest() throws Exception
	{	
		
		/**
		 * generate UserDTO w/ not valid Email
		 * @entity
		 */
		UserDTO nonvalido = new UserDTO();
		nonvalido.setEmail("NotValidEmail");
		nonvalido.setType("");
		
		/**
		 * Check if the not-valid element in user is 
		 * equals to the error code not accettable (bad request // 406)
		 * @test
		 */
		String dtoJsonizzatoNonValido = _asJsonString(nonvalido);
		mock.perform(MockMvcRequestBuilders.post("/addGuest")
										   .header("Authorization", tokenEmployee)
										   .content(dtoJsonizzatoNonValido)
										   .contentType(MediaType.APPLICATION_JSON)
										   .accept(MediaType.APPLICATION_JSON))
			.andExpect(status()
				.isNotAcceptable());
		
		/**
		 * generate UserDTO w/ not valid Type
		 * @entity
		 */
		nonvalido.setEmail("valid@email.com");
		nonvalido.setType("NotValidType");
		
		/**
		 * Check if the not-valid element in user is 
		 * equals to the error code not accettable (bad request // 406)
		 * @test
		 */
		dtoJsonizzatoNonValido = _asJsonString(nonvalido);
		mock.perform(MockMvcRequestBuilders.post("/addGuest")
										   .header("Authorization", tokenEmployee)
										   .content(dtoJsonizzatoNonValido)
										   .contentType(MediaType.APPLICATION_JSON)
										   .accept(MediaType.APPLICATION_JSON))
			.andExpect(status()
				.isNotAcceptable());
		
		/**
		 * generate not valid json
		 * @entity
		 */
		String jsonBrutto =" \"id\": 2,\r\n"
				+ "    \"username\": \"marior\",\r\n"
				+ "    \"nome\": \"Mario\",\r\n"
				+ "    \"cognome\": \"Rossi\",\r\n"
				+ "    \"indirizzo\": \"Roma Via Roma 123\"";
		/**
		 * Send the not-valid json in user
		 * Check if the status is
		 * equals to the error code not accettable (bad request // 406)
		 * @test
		 */
		mock.perform(MockMvcRequestBuilders.post("/addGuest")
										   .header("Authorization", tokenEmployee)
										   .content(jsonBrutto)
										   .contentType(MediaType.APPLICATION_JSON)
										   .accept(MediaType.APPLICATION_JSON))
			.andExpect(status()
				.isBadRequest());	
	}
	
	@Test
	void testAddEmployee() throws Exception
	{	
		/**
		 * generate UserDTO w/ not valid Email
		 * @entity
		 */
		UserDTO nonvalido = new UserDTO();
		nonvalido.setEmail("NotValidEmail");
		nonvalido.setType("");
		
		/**
		 * Check if the not-valid element in user is 
		 * equals to the error code not accettable (bad request // 406)
		 * @test
		 */
		String dtoJsonizzatoNonValido = _asJsonString(nonvalido);
		mock.perform(MockMvcRequestBuilders.post("/addEmployee")
										   .header("Authorization", tokenEmployee)
										   .content(dtoJsonizzatoNonValido)
										   .contentType(MediaType.APPLICATION_JSON)
										   .accept(MediaType.APPLICATION_JSON))
			.andExpect(status()
				.isNotAcceptable());
		
		/**
		 * generate UserDTO w/ not valid Type
		 * @entity
		 */
		nonvalido.setEmail("valid@email.com");
		nonvalido.setType("NotValidType");
		
		/**
		 * Check if the not-valid element in user is 
		 * equals to the error code not accettable (bad request // 406)
		 * @test
		 */
		dtoJsonizzatoNonValido = _asJsonString(nonvalido);
		mock.perform(MockMvcRequestBuilders.post("/addEmployee")
										   .header("Authorization", tokenEmployee)
										   .content(dtoJsonizzatoNonValido)
										   .contentType(MediaType.APPLICATION_JSON)
										   .accept(MediaType.APPLICATION_JSON))
			.andExpect(status()
				.isNotAcceptable());
		
		/**
		 * generate not valid json
		 * @entity
		 */
		String jsonBrutto =" \"id\": 2,\r\n"
				+ "    \"username\": \"marior\",\r\n"
				+ "    \"nome\": \"Mario\",\r\n"
				+ "    \"cognome\": \"Rossi\",\r\n"
				+ "    \"indirizzo\": \"Roma Via Roma 123\"";
		/**
		 * Send the not-valid json in user
		 * Check if the status is
		 * equals to the error code not accettable (bad request // 406)
		 * @test
		 */
		mock.perform(MockMvcRequestBuilders.post("/addEmployee")
				   						   .header("Authorization", tokenEmployee)
										   .content(jsonBrutto)
										   .contentType(MediaType.APPLICATION_JSON)
										   .accept(MediaType.APPLICATION_JSON))
			.andExpect(status()
				.isBadRequest());
	}
	
	void testGetBookedRooms() throws Exception
	{
		/**
		 * Check if the first element in user is 
		 * equals to the json of the first user's bookings got from postman
		 * as an employee
		 * @test
		 */
		mock.perform(MockMvcRequestBuilders.get("/user/1/bookedrooms").header("Authorization", tokenEmployee))  
		.andDo(print())											
		.andExpect(status()
			.isOk())								
		.andExpect(MockMvcResultMatchers.content()
			.json(userbookingsjson));	
		
		/**
		 * Check if the first element in user is 
		 * equals to the error code unauthorized request 
		 * && gives the string "So cosa stai cercando di fare, brutto pagliaccio"
		 * as a guest
		 * @test
		 */
		mock.perform(MockMvcRequestBuilders.get("/user/1/bookedrooms").header("Authorization", tokenEmployee))  
		.andDo(print())											
		.andExpect(status()
			.isUnauthorized())								
		.andExpect(MockMvcResultMatchers.content()
			.string("So cosa stai cercando di fare, brutto pagliaccio"));	
		
		/**
		 * Check if the invalid link in user is 
		 * equals to the error code bad request 
		 * && gives the string "Controlla se stai inviando il parametro corretto"
		 * @test
		 */
		mock.perform(MockMvcRequestBuilders.get("/user/paperino/bookedrooms").header("Authorization", tokenEmployee))  
		.andDo(print())											
		.andExpect(status()
			.isBadRequest())								
		.andExpect(MockMvcResultMatchers.content()
			.string("Controlla se stai inviando il parametro corretto"));
		
		/**
		 * Check if the invalid element in user is 
		 * equals to the error code not found
		 * && gives the string "sei sotto effetto di droghe fra"
		 * @test
		 */
		mock.perform(MockMvcRequestBuilders.get("/user/255/bookedrooms").header("Authorization", tokenEmployee))  
		.andDo(print())											
		.andExpect(status()
			.isNotFound())								
		.andExpect(MockMvcResultMatchers.content()
			.string("sei sotto effetto di droghe fra"));
	}
	
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
	
	String userjson = "{\r\n"
			+ "    \"id\": 1,\r\n"
			+ "    \"email\": \"vipguest@gmail.com\",\r\n"
			+ "    \"type\": \"guest\",\r\n"
			+ "    \"bookings\": [\r\n"
			+ "        {\r\n"
			+ "            \"id\": 1,\r\n"
			+ "            \"check_in_date\": \"2023-10-28\",\r\n"
			+ "            \"check_out_date\": \"2023-10-30\",\r\n"
			+ "            \"price\": 50.0,\r\n"
			+ "            \"num_of_guests\": 1,\r\n"
			+ "            \"guest_information\": \"early check-in requested\",\r\n"
			+ "            \"room\": {\r\n"
			+ "                \"id\": 1,\r\n"
			+ "                \"roomName\": \"a\",\r\n"
			+ "                \"notes\": \"\",\r\n"
			+ "                \"base_price\": 50.0,\r\n"
			+ "                \"capacity\": 1\r\n"
			+ "            }\r\n"
			+ "        }\r\n"
			+ "    ]\r\n"
			+ "}";
	
	String userbookingsjson = "[\r\n"
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
			+ "        }\r\n"
			+ "    }\r\n"
			+ "]";
}
