package com.shark.base.test.worker;

import java.util.HashMap;


public interface ITestWorker {
	
	public ResponseResultEntity get(String host, String apiPath, HashMap<String, String> headers, String queryString, boolean debug) throws Exception;

	public ResponseResultEntity post(String host, String apiPath, HashMap<String, String> headers,
                                     HashMap<String, String> bodyHashMap, boolean debug) throws Exception;
}
