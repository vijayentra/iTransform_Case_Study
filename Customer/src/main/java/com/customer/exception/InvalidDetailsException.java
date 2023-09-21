package com.customer.exception;

public class InvalidDetailsException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;

	public InvalidDetailsException(String msg) {
		super(msg);
	}
}