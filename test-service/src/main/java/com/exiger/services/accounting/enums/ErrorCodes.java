package com.exiger.services.accounting.enums;

public enum ErrorCodes {

	DEFAULT_ERROR_CODE("DefaultErrorCode");

	String errorCode;

	ErrorCodes(final String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorCode() {
		return errorCode;
	}

}
