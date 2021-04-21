package com.revature.exceptions;

public class BrokenBalanceException extends Exception {

	public BrokenBalanceException() {
		super();
	}

	public BrokenBalanceException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public BrokenBalanceException(String message, Throwable cause) {
		super(message, cause);
	}

	public BrokenBalanceException(String message) {
		super(message);
	}

	public BrokenBalanceException(Throwable cause) {
		super(cause);
	}

}
