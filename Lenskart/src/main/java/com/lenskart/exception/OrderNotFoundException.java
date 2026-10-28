package com.lenskart.exception;

public class OrderNotFoundException extends RuntimeException{

	public OrderNotFoundException(String _message) {
		super(_message);
	}
}
