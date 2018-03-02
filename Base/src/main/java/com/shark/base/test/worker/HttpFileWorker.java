package com.shark.base.test.worker;

import com.shark.base.util.QueryStringUtil;
import com.shark.base.util.StringUtil;

import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class HttpFileWorker implements ITestWorker {

	private static final int BUFFER_SIZE = 4096;
	
	private String fileName;
	
	public HttpFileWorker(String fileName) {
		this.fileName = fileName;
	}
	

	public ResponseResultEntity get(String host, String apiPath, HashMap<String, String> headers,
                                    String queryString, boolean debug) throws Exception {
		
		ResponseResultEntity result = new ResponseResultEntity();
		URL url;
		try {
			String urlString = host + apiPath;
			if(!StringUtil.isEmpty(queryString)) {
				urlString = urlString + "?" + queryString; 
			}
			url = new URL(urlString);
			HttpURLConnection connection;
			connection = (HttpURLConnection) url.openConnection();
	        connection.setRequestMethod("GET");
	        if(headers != null) {
				for(String key: headers.keySet()) {
					connection.setRequestProperty(key, headers.get(key));
					
				}
			}

	        int statusCode = connection.getResponseCode();
	        Map<String,List<String>> reponseHeaders = connection.getHeaderFields();
	        
	        
	        InputStream inputStream = connection.getInputStream();
	        FileOutputStream outputStream = new FileOutputStream(fileName);
            int bytesRead = -1;
            byte[] buffer = new byte[BUFFER_SIZE];
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            outputStream.close();
            inputStream.close(); 
	        
	        result.setStatusCode(statusCode);
	        result.setHeaders(reponseHeaders);
	        
	        if(debug) {
	        		System.out.println("url: " + url);
	        		System.out.println("response code : " + statusCode);
	        		
	        		for(String key: reponseHeaders.keySet()) {
		        		System.out.println("header key: " + key);
		        		for(String value: reponseHeaders.get(key)) {
		        			System.out.println("header value: " + value);
		        		}
		        }
    				
    			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}  catch (IOException e) {
			e.printStackTrace();
		}
		return result;	
	}

	public ResponseResultEntity post(String host, String apiPath, HashMap<String, String> headers,
                                     HashMap<String, String> bodyHashMap, boolean debug) throws Exception {
		ResponseResultEntity result = new ResponseResultEntity();
		URL url;
		try {
			String urlString = host + apiPath;
			url = new URL(urlString);
			HttpURLConnection.setFollowRedirects(true);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("POST");
		
			if(headers != null) {
				for(String key: headers.keySet()) {
					connection.setRequestProperty(key, headers.get(key));
				}
			}
			
			connection.setUseCaches(false);
			connection.setAllowUserInteraction(false);
			connection.setInstanceFollowRedirects(false);
			connection.setDoOutput(true);
	        DataOutputStream dataOutputStream = new DataOutputStream(connection.getOutputStream());
	        String body = QueryStringUtil.generateQueryString(bodyHashMap);
	        dataOutputStream.writeBytes(body);
	        dataOutputStream.flush();
	        dataOutputStream.close();
	        
	        int statusCode = connection.getResponseCode();
	        Map<String,List<String>> responseHeaders = connection.getHeaderFields();
	        InputStream inputStream = connection.getInputStream();
	        FileOutputStream outputStream = new FileOutputStream(fileName);
            int bytesRead = -1;
            byte[] buffer = new byte[BUFFER_SIZE];
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            outputStream.close();
            inputStream.close(); 
            
	        result.setStatusCode(statusCode);
	        result.setHeaders(responseHeaders);
	        
	        if(debug) {
	        		System.out.println("url: " + url);
	        		System.out.println("body : " + body);
	        		System.out.println("response code : " + statusCode);
	        		for(String key: responseHeaders.keySet()) {
		        		System.out.println("header key: " + key);
		        		for(String value: responseHeaders.get(key)) {
		        			System.out.println("header value: " + value);
		        		}
		        }
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

}
