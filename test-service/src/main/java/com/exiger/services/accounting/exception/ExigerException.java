package com.exiger.services.accounting.exception;

import com.exiger.services.accounting.enums.ErrorCodes;

public class ExigerException extends Exception {

	private String serviceName = "test-service";
	private String errorMessage;
	private ErrorCodes errorCode;

	public ExigerException(final ErrorCodes errorCode, final String errorMessage, final Exception exception) {
		super(exception);
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}

	public String getServiceName() {
		return serviceName;
	}

	public ErrorCodes getErrorCode() {
		return errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}
}

