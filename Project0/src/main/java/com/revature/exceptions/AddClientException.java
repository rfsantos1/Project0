package com.revature.exceptions;

public class AddClientException extends Exception {

	public AddClientException() {
		super();
	}

	public AddClientException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public AddClientException(String message, Throwable cause) {
		super(message, cause);
	}

	public AddClientException(String message) {
		super(message);
	}

	public AddClientException(Throwable cause) {
		super(cause);
	}

}
