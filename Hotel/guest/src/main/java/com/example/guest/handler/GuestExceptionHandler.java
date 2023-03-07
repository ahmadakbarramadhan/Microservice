package com.example.guest.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.guest.exception.InvalidAuthException;

@RestControllerAdvice
public class GuestExceptionHandler {
	@ExceptionHandler
	public ResponseEntity<String> handleInvalidAuthException(InvalidAuthException exception) {
		return new ResponseEntity<String>(exception.getMessage(), HttpStatus.PRECONDITION_FAILED);
	}
}
