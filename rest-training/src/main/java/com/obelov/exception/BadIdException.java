package com.obelov.exception;

public class BadIdException extends RuntimeException {
	public BadIdException(String message) {
		super(message);
	}

	public BadIdException(String message, Throwable cause) {
		super(message, cause);
	}

}
