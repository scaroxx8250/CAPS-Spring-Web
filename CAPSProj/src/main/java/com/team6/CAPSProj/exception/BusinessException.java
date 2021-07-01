package com.team6.CAPSProj.exception;

@SuppressWarnings("serial")
public class BusinessException extends Exception {
	
	public BusinessException() {
		super();
	}
	
	public BusinessException(String message) {
		super(message);
	}

}
