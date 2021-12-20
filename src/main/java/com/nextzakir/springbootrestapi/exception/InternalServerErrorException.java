package com.nextzakir.springbootrestapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class InternalServerErrorException extends RuntimeException {
	private static final long serialVersionUID = -2927762559336817363L;
	
	public InternalServerErrorException(String errorMessage) {
		super(errorMessage);
	}
}
