package com.storage.exceptions;

import lombok.Getter;

@Getter
public class ResourceNotFoundException extends RuntimeException{
	private static final long serialVersionUID = 1L;

	String resourceName;
	String field;
	String fieldName;
	Long fieldId;
	
	public ResourceNotFoundException(String resourceName, String field, String fieldName) {
		super(String.format("%s not found with %s: %s", resourceName, field,fieldName));
		this.resourceName = resourceName;
		this.field = field;
		this.fieldName = fieldName;
	}
	public ResourceNotFoundException(String resourceName, String field, Long fieldId) {
		super(String.format("%s not found with %s: %s",resourceName, field, fieldId));
		this.resourceName = resourceName;
		this.field = field;
		this.fieldId = fieldId;
	}
	
	
	public ResourceNotFoundException(String resourceName) {
	    super(String.format( resourceName));
	    this.resourceName = resourceName;
	}

	public ResourceNotFoundException() {
		super("Resource not found!");
	}
	
	
	
}