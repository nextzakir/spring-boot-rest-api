package com.nextzakir.springbootrestapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CustomNotFoundException extends RuntimeException {
	private static final long serialVersionUID = -2927762559336817363L;
	
	public CustomNotFoundException(String errorMessage) {
		super(errorMessage);
	}
}
