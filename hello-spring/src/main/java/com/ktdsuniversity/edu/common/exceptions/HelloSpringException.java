package com.ktdsuniversity.edu.common.exceptions;

public class HelloSpringException extends RuntimeException {

	private static final long serialVersionUID = -254738828445974509L;

	private String viewName;
	private String modelKey;
	private Object modelValue;

	public HelloSpringException(String message) {
		super(message);
	}
	
	public HelloSpringException(String message, String viewName) {
		super(message);
		this.viewName = viewName;
	}
	
	public HelloSpringException(String message, String viewName, 
							    String modelKey, Object modelValue) {
		super(message);
		this.viewName = viewName;
		this.modelKey = modelKey;
		this.modelValue = modelValue;
	}
	
	public String getViewName() {
		return viewName;
	}

	public void setViewName(String viewName) {
		this.viewName = viewName;
	}

	public String getModelKey() {
		return modelKey;
	}

	public void setModelKey(String modelKey) {
		this.modelKey = modelKey;
	}

	public Object getModelValue() {
		return modelValue;
	}

	public void setModelValue(Object modelValue) {
		this.modelValue = modelValue;
	}


}
