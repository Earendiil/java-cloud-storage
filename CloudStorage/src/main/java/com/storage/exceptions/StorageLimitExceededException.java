package com.storage.exceptions;

public class StorageLimitExceededException extends RuntimeException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public StorageLimitExceededException(String message) {
        super(message);
    }
}
