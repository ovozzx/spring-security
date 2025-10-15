package com.ktdsuniversity.edu.common.vo;

import java.util.Map;

public class AjaxResponse {

	private Map<String, Object> error;
	private Object body;

	public Map<String, Object> getError() {
		return error;
	}

	public void setError(Map<String, Object> error) {
		this.error = error;
	}

	public Object getBody() {
		return body;
	}

	public void setBody(Object body) {
		this.body = body;
	}

}
