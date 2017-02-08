package com.hnair.iot.dataserver;

/**
 * The root exception for the IAM.
 * 
 * @author Bin.Zhang
 */
public class IamException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	protected static final int ERROR_CODE_UNKOWN = -1;

	// this code should include HTTP status code to simplify the exception model
	// e.g. 400_101, 400 means http bad request, 101 is the specific code for the domain.
	// code=400_101
	// statusCode=400
	// errorCode=101
	// TODO validate code to ensure no duplicated code or malformed at start time or build time ?
	private final int code;

	public IamException(int code) {
		this.code = code;
	}

	public IamException(int code, String message) {
		super(message);
		this.code = code;
	}

	public IamException(int code, Throwable cause) {
		super(cause);
		this.code = code;
	}

	public IamException(int code, String message, Throwable cause) {
		super(message, cause);
		this.code = code;
	}

	/**
	 * Return the code of the exception.
	 * 
	 * @return the code
	 */
	public int getCode() {
		return code;
	}

	/**
	 * Returns the possible HTTP status code.
	 * 
	 * @return the HTTP status code
	 */
	public int getStatusCode() {
		return Integer.valueOf(String.valueOf(code).substring(0, 3));
	}

	/**
	 * Return the specific error code for a domain
	 * 
	 * @return error code
	 */
	public int getErrorCode() {
		return Integer.valueOf(String.valueOf(code).substring(3));
	}
}
