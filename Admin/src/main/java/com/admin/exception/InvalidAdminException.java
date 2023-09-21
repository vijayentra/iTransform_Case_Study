package com.admin.exception;

public class InvalidAdminException extends RuntimeException{

	public InvalidAdminException() {
		super();
	}

	public InvalidAdminException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public InvalidAdminException(String message, Throwable cause) {
		super(message, cause);
	}

	public InvalidAdminException(String message) {
		super(message);
	}

	public InvalidAdminException(Throwable cause) {
		super(cause);
	}
	
}
