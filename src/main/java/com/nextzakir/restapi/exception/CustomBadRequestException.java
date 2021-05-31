package com.nextzakir.restapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CustomBadRequestException extends RuntimeException {
	private static final long serialVersionUID = -2927762559336817363L;
	
	public CustomBadRequestException(String errorMessage) {
		super(errorMessage);
	}
}
