package com.senlainc.library.exception;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {
	
	private final String INCORRECT_REQUEST = "INCORRECT_REQUEST";
	private final String BAD_REQUEST = "BAD_REQUEST";
	private final String ACCESS_DENIED = "ACCESS_DENIED";
	
	@ExceptionHandler(RecordNotFoundException.class)
	public final ResponseEntity<ErrorResponse> handleUserNotFoundException
						(RecordNotFoundException ex, WebRequest request) 
	{
		List<String> details = new ArrayList<>();
		details.add(ex.getLocalizedMessage());
		ErrorResponse error = new ErrorResponse(INCORRECT_REQUEST, details);
		return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(MissingHeaderInfoException.class)
	public final ResponseEntity<ErrorResponse> handleInvalidTraceIdException
						(MissingHeaderInfoException ex, WebRequest request) {
		List<String> details = new ArrayList<>();
		details.add(ex.getLocalizedMessage());
		ErrorResponse error = new ErrorResponse(BAD_REQUEST, details);
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(ConstraintViolationException.class)
	public final ResponseEntity<ErrorResponse> handleConstraintViolation(
											ConstraintViolationException ex,
											WebRequest request)
	{
		List<String> details = ex.getConstraintViolations()
									.parallelStream()
									.map(e -> e.getMessage())
									.collect(Collectors.toList());

		ErrorResponse error = new ErrorResponse(BAD_REQUEST, details);
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(AccessDeniedException.class)
	public final ResponseEntity<ErrorResponse> handleAccessDeniedException(
											AccessDeniedException ex,
											WebRequest request)
	{
		List<String> details = new ArrayList<>();
		details.add(ex.getLocalizedMessage());

		ErrorResponse error = new ErrorResponse(ACCESS_DENIED, details);
		return new ResponseEntity<>(error, HttpStatus.FORBIDDEN);
	}
}
