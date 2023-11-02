package com.generation.javago.controller.util;

import java.util.NoSuchElementException;

import org.springframework.dao.InvalidDataAccessResourceUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

/**
 * controller for handling personal exceptions 
 * @author ABelli
 *
 */
@RestControllerAdvice
public class ExceptionController 
{
	/**
	 * Facciamo un catch su tutti i NoSuchElementException nei RestController, e gli ritorniamo un messaggio specifico e un no found
	 * @param e
	 * @return
	 */
	@ExceptionHandler(NoSuchElementException.class) //come catch(NoSuchElementException e)
	public  ResponseEntity <String> handleNoSuchElementException(NoSuchElementException e)
	{
		return new ResponseEntity <String>(e.getMessage(), HttpStatus.NOT_FOUND);
		
	}
	
	/**
	 * Facciamo un catch per vedere se dei json inviati risultano corrotti, e poi restituiamo una BAD REQUEST
	 * @param e
	 * @return
	 */
	@ExceptionHandler(HttpMessageNotReadableException.class) 
	public  ResponseEntity <String> handleHttpMessageNotReadableException(HttpMessageNotReadableException e)
	{
		return new ResponseEntity <String>("Il tuo JSON fa schifo", HttpStatus.BAD_REQUEST);
	}
	
	/**
	 * Controlliamo la connessione al DB, in caso di catch avvertiamo del db fuori uso
	 * @param e
	 * @return
	 */
	@ExceptionHandler(InvalidDataAccessResourceUsageException.class) 
	public  ResponseEntity <String> handleInvalidDataAccessResourceUsageException(InvalidDataAccessResourceUsageException e)
	{
		return new ResponseEntity <String>("db fuori uso", HttpStatus.SERVICE_UNAVAILABLE);
		
	}
	
	/**
	 * Controlla se i parametri invaiti siano del tipo corretto
	 * @param e
	 * @return
	 */
	@ExceptionHandler(MethodArgumentTypeMismatchException.class) 
	public  ResponseEntity <String> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e)
	{
		return new ResponseEntity <String>("Controlla se stai inviando il parametro corretto", HttpStatus.BAD_REQUEST);
	}
	
	
	@ExceptionHandler(InvalidEntityException.class) 
	public  ResponseEntity <String> handleInvalidEntityException(InvalidEntityException e)
	{
		return new ResponseEntity <String>(e.getMessage(), HttpStatus.NOT_ACCEPTABLE);
	}
	
	@ExceptionHandler(UnAuthorizedException.class) 
	public  ResponseEntity <String> handleUnAuthorizedException(UnAuthorizedException e)
	{
		return new ResponseEntity <String>(e.getMessage(), HttpStatus.UNAUTHORIZED);
	}
	

}
