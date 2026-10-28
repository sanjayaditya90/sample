package com.lenskart.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.lenskart.response.ErrorResponse;

@RestControllerAdvice
public class GlobalExceptionHandler{

	@ExceptionHandler(OrderNotCreatedException.class)
	public ResponseEntity<ErrorResponse> handleOrderNotCreatedException(OrderNotCreatedException e) {
		
		ErrorResponse response = new ErrorResponse();
		
		response.setErrorCode("ER-001");
		response.setMessage(e.getMessage());
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	}
	
	@ExceptionHandler(OrderNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleOrderNotFoundException(OrderNotFoundException e) {
		
		ErrorResponse response = new ErrorResponse();
		
		response.setErrorCode("ER-002");
		response.setMessage(e.getMessage());
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	}
}
