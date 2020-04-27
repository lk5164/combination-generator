package com.hsu.backend.controller.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

/**
 * Exception for case when requested job is not found.
 * @see HttpStatus#NOT_FOUND
 */
public class InvalidInputException extends ResponseStatusException {

  public static final HttpStatus HTTP_STATUS = HttpStatus.NOT_ACCEPTABLE;

  public static InvalidInputException createForItem (String phoneNumber) {
    return new InvalidInputException("Input phone number is not valid: " + phoneNumber);
  }

  public InvalidInputException(String reason) {
    super(HTTP_STATUS, reason);
  }

  public InvalidInputException(String reason, Throwable cause) {
    super(HTTP_STATUS, reason, cause);
  }
}
