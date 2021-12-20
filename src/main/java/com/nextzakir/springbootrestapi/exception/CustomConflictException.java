package com.nextzakir.springbootrestapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class CustomConflictException extends RuntimeException {
	private static final long serialVersionUID = -2927762559336817363L;

	public CustomConflictException(String errorMessage) {
		super(errorMessage);
	}
}
