package com.shark.base.test.worker;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.util.StringUtils;

import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MockFileWorker implements ITestWorker {

	private MockMvc mockMvc;
	private String fileName;
	
	public MockFileWorker(MockMvc mockMvc, String fileName) {
		this.mockMvc = mockMvc;
		this.fileName = fileName;
	}

	public ResponseResultEntity get(String host, String apiPath, HashMap<String, String> headers, String queryString, boolean debug) throws Exception {
		
		ResponseResultEntity result = new ResponseResultEntity();
		
		String urlTemplate = apiPath;
		if(!StringUtils.isEmpty(queryString)) {
			urlTemplate = urlTemplate + "?" + queryString; 
		}
		
		MockHttpServletRequestBuilder requestBuilder = 
				MockMvcRequestBuilders.get(urlTemplate).accept(MediaType.APPLICATION_JSON);
		
		if(headers != null) {
			HttpHeaders httpHeaders = new HttpHeaders();
			for(String key: headers.keySet()) {
				headers.put(key, headers.get(key));
				
			}
			requestBuilder.headers(httpHeaders);
		}

		ResultActions resultAction = mockMvc.perform(requestBuilder);
		MockHttpServletResponse response = 
				resultAction.andDo(MockMvcResultHandlers.print()).andReturn().getResponse();

		int statusCode = response.getStatus();
		result.setStatusCode(statusCode);
		
		Map<String,List<String>> responseHeaders = new HashMap<String, List<String>>();
		for(String headerName: response.getHeaderNames()) {
			responseHeaders.put(headerName, response.getHeaders(headerName));
		}
		result.setHeaders(responseHeaders);
		
		FileOutputStream outputStream = new FileOutputStream(fileName);
		byte[] buffer = response.getContentAsByteArray();
		outputStream.write(buffer, 0, buffer.length);
		outputStream.close();
		
		if(debug) {
	        System.out.println("apiPath: " + apiPath);
	        System.out.println("response code : " + statusCode);
	        for(String key: responseHeaders.keySet()) {
	        		System.out.println("header key: " + key);
	        		for(String value: responseHeaders.get(key)) {
	        			System.out.println("header value: " + value);
	        		}
	        }
        }
        
		return result;
	}

	public ResponseResultEntity post(String host, String apiPath, HashMap<String, String> headers, HashMap<String, String> bodyHashMap, boolean debug) throws Exception {
		ResponseResultEntity result = new ResponseResultEntity();
		
		MockHttpServletRequestBuilder requestBuilder = 
				MockMvcRequestBuilders.post(apiPath).accept(MediaType.APPLICATION_JSON);
		
		if(headers != null) {
			HttpHeaders httpHeaders = new HttpHeaders();
			for(String key: headers.keySet()) {
				headers.put(key, headers.get(key));
				
			}
			requestBuilder.headers(httpHeaders);
		}
		
		for(String key: bodyHashMap.keySet()) {
			requestBuilder.param(key, bodyHashMap.get(key));
		}
		
		ResultActions resultAction = mockMvc.perform(requestBuilder);
		MockHttpServletResponse response = 
				resultAction.andDo(MockMvcResultHandlers.print()).andReturn().getResponse();
		
		
		int statusCode = response.getStatus();
		result.setStatusCode(statusCode);
		
		Map<String,List<String>> responseHeaders = new HashMap<String, List<String>>();
		for(String headerName: response.getHeaderNames()) {
			responseHeaders.put(headerName, response.getHeaders(headerName));
		}
		result.setHeaders(responseHeaders);
		
		FileOutputStream outputStream = new FileOutputStream(fileName);
		byte[] buffer = response.getContentAsByteArray();
		outputStream.write(buffer, 0, buffer.length);
		outputStream.close();
		
		if(debug) {
	        System.out.println("apiPath: " + apiPath);
	        System.out.println("response code: " + statusCode);
	        for(String key: responseHeaders.keySet()) {
	        		System.out.println("header key: " + key);
	        		for(String value: responseHeaders.get(key)) {
	        			System.out.println("header value: " + value);
	        		}
	        }
        }
        
		return result;
	}

}
