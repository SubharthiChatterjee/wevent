package com.wooplr.commons;

/**
 * @author subharthi chatterjee
 * 
 */
public class DataAccessException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int errorCode;
	private Exception exception;
	private String message;

	public DataAccessException(int errorCode) {
		this.errorCode = errorCode;
	}

	public DataAccessException(Exception ee) {
		this.exception = ee;
		this.message = ee.getMessage();
	}

	public DataAccessException(int errorCode, String message) {
		this.errorCode = errorCode;
		this.message = message;
	}

	public DataAccessException(Exception ee, int errorCode) {
		this.exception = ee;
		this.errorCode = errorCode;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String getMessage() {
		return this.message;
	}

	/**
	 * @return the errorCode
	 */
	public int getErrorCode() {
		return errorCode;
	}

	/**
	 * @return the exception
	 */
	public Exception getException() {
		return exception;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("DataAccessException [errorCode=").append(errorCode).append(", exception=").append(exception)
				.append(", message=").append(message).append("]");
		return builder.toString();
	}
}