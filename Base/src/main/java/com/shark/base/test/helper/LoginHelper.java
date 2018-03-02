package com.shark.base.test.helper;


import com.shark.base.test.worker.HttpWorker;
import com.shark.base.test.worker.ITestWorker;
import com.shark.base.test.worker.MockWorker;
import com.shark.base.test.worker.ResponseResultEntity;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoginHelper {

	private ITestWorker worker;

	public LoginHelper(MockMvc mockMvc) {
		worker = new MockWorker(mockMvc);
	}


	public LoginHelper() {
		worker = new HttpWorker();
	}
	
	public String login(String host, String account, String password)  throws Exception {
		HashMap<String, String> bodyMap = new HashMap();
		bodyMap.put("account", account);
		bodyMap.put("password", password);
		ResponseResultEntity loginResult = worker.post(host,"/login", new HashMap<String, String>(), bodyMap, false);
		Map<String, List<String>> headers = loginResult.getHeaders();
		String authorization = String.valueOf(headers.get("Authorization").get(0));
		return authorization;
	}
}
