package com.nextzakir.restapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class CustomInternalServerErrorException extends RuntimeException {
	private static final long serialVersionUID = -2927762559336817363L;
	
	public CustomInternalServerErrorException(String errorMessage) {
		super(errorMessage);
	}
}
