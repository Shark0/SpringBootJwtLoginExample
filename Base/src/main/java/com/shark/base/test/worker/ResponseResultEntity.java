package com.shark.base.test.worker;

import java.util.List;
import java.util.Map;

public class ResponseResultEntity {
	
	private int statusCode;
	
	private Map<String, List<String>> headers;
	
	private String content;

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public Map<String, List<String>> getHeaders() {
		return headers;
	}

	public void setHeaders(Map<String, List<String>> headers) {
		this.headers = headers;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}
