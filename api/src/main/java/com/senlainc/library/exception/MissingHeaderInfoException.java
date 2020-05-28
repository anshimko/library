package com.senlainc.library.exception;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class MissingHeaderInfoException extends RuntimeException
{
	private static final long serialVersionUID = 1L;

	public MissingHeaderInfoException(String message) {
        super(message);
    }
}
