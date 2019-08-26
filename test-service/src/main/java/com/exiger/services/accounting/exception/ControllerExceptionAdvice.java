package com.exiger.services.accounting.exception;

import com.exiger.services.accounting.enums.ErrorCodes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice(basePackages = "com.exiger.services.translation.controllers")
public class ControllerExceptionAdvice extends ResponseEntityExceptionHandler {

	@ExceptionHandler(value = {ExigerException.class})
	protected ResponseEntity<Object> exigerErrorHandler(ExigerException ex, WebRequest request) {
		return handleExceptionInternal(ex, createJsonResponse(ex.getErrorMessage(), ex.getErrorCode()),
				new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
	}

	@ExceptionHandler(value = {RuntimeException.class})
	protected ResponseEntity<Object> generalExceptionErrorHandler(RuntimeException ex, WebRequest request) {
		return handleExceptionInternal(ex, createJsonResponse(ex.getCause().getMessage(), ErrorCodes.DEFAULT_ERROR_CODE),
				new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
	}

	private Map<String, String> createJsonResponse(final String message, final ErrorCodes errorCode) {
		Map<String, String> response = new HashMap<>();
		response.put("message", message);
		response.put("errorCode", errorCode.toString());
		return response;
	}

}
