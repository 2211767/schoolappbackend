package com.example.demo.exceptions;

public class UserException extends Throwable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String message;

	public UserException(String msg) {
		super(msg);
		this.message = msg;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
