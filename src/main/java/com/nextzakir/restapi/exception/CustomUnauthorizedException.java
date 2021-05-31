package com.nextzakir.restapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class CustomUnauthorizedException extends RuntimeException {
	private static final long serialVersionUID = -2927762559336817363L;
	
	public CustomUnauthorizedException(String errorMessage) {
		super(errorMessage);
	}
}
