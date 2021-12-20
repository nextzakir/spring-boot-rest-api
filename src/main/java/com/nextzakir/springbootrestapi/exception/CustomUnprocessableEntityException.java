package com.nextzakir.springbootrestapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class CustomUnprocessableEntityException extends RuntimeException {
	private static final long serialVersionUID = -2927762559336817363L;
	
	public CustomUnprocessableEntityException(String errorMessage) {
		super(errorMessage);
	}
}
