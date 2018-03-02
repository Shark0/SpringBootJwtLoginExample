package com.shark.base.test.worker;

import com.shark.base.util.StringUtil;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MockWorker implements ITestWorker {

	private MockMvc mockMvc;

	public MockWorker(MockMvc mockMvc) {
		this.mockMvc = mockMvc;
	}

	public ResponseResultEntity get(String host, String apiPath, HashMap<String, String> headers, String queryString, boolean debug) throws Exception {
		
		ResponseResultEntity result = new ResponseResultEntity();
		
		String urlTemplate = apiPath;
		if(!StringUtil.isEmpty(queryString)) {
			urlTemplate = urlTemplate + "?" + queryString; 
		}
		
		MockHttpServletRequestBuilder requestBuilder =
				MockMvcRequestBuilders.get(urlTemplate).accept(MediaType.APPLICATION_JSON);
		
		if(headers != null) {
			HttpHeaders httpHeaders = new HttpHeaders();
			for(String key: headers.keySet()) {
				String value = headers.get(key);
				if(debug) {
					System.out.println("MockWorker get() headers key: " + key + ", value: " + value);
				}
				requestBuilder.header(key, value);
				
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
		
		String responseContent = response.getContentAsString();
		result.setContent(responseContent);
		if(debug) {
	        System.out.println("apiPath: " + apiPath);
	        System.out.println("response code : " + statusCode);
	        for(String key: responseHeaders.keySet()) {
	        		System.out.println("header key: " + key);
	        		for(String value: responseHeaders.get(key)) {
	        			System.out.println("header value: " + value);
	        		}
	        }
	        System.out.println("response content: " + responseContent);
        }
        
		return result;
	}


	public ResponseResultEntity post(String host, String apiPath, HashMap<String, String> headers, HashMap<String, String> bodyHashMap, boolean debug) throws Exception {
		ResponseResultEntity result = new ResponseResultEntity();
		
		MockHttpServletRequestBuilder requestBuilder =
				MockMvcRequestBuilders.post(apiPath).accept(MediaType.APPLICATION_JSON);

		if(debug) {
			System.out.println("MockWorker post() headers != null: " + (headers != null));
		}
		if(headers != null) {
			for(String key: headers.keySet()) {
				String value = headers.get(key);
				if(debug) {
					System.out.println("MockWorker post() headers key: " + key + ", value: " + value);
				}
				requestBuilder.header(key, value);
			}
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
		
		String responseContent = response.getContentAsString();
		result.setContent(responseContent);
		if(debug) {
	        System.out.println("apiPath: " + apiPath);
	        System.out.println("response code: " + statusCode);
	        for(String key: responseHeaders.keySet()) {
	        		System.out.println("header key: " + key);
	        		for(String value: responseHeaders.get(key)) {
	        			System.out.println("header value: " + value);
	        		}
	        }
	        System.out.println("response content: " + responseContent);
        }
        
		return result;
	}

}
