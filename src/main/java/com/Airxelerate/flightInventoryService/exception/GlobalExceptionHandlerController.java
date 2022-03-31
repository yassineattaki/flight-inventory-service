package com.Airxelerate.flightInventoryService.exception;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandlerController {

	  @ExceptionHandler(CustomException.class)
	  public void handleCustomException(HttpServletResponse res, CustomException ex) throws IOException {
	    res.sendError(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
	  }

	  @ExceptionHandler(AccessDeniedException.class)
	  public void handleAccessDeniedException(HttpServletResponse res, AccessDeniedException ex) throws IOException {
	    res.sendError(HttpStatus.FORBIDDEN.value(), "Access denied");
	  }

	  @ExceptionHandler(Exception.class)
	  public void handleException(HttpServletResponse res, Exception ex) throws IOException {
	    res.sendError(HttpStatus.BAD_REQUEST.value(), "Something went wrong");
	  }

}
