package com.exiger.services.accounting.tests;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import com.exiger.services.accounting.exception.ExigerException;
import com.exiger.services.accounting.enums.ErrorCodes;

public class ExigerExceptionTest {

	private static final String ERROR_MESSAGE = "The engine google is not supported";

	@Test
	public void validateExceptionStructure() {
		ExigerException exceptionUtils = new ExigerException(ErrorCodes.DEFAULT_ERROR_CODE, ERROR_MESSAGE, mock(Exception.class));
		assertEquals("test-service", exceptionUtils.getServiceName());
		assertEquals(ErrorCodes.DEFAULT_ERROR_CODE, exceptionUtils.getErrorCode());
		assertEquals(ERROR_MESSAGE, exceptionUtils.getErrorMessage());
	}
}