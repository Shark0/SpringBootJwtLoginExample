package com.shark.base.test.scenario;


import com.google.gson.Gson;
import com.shark.base.entity.ResponseEntity;
import com.shark.base.test.listener.TestEventListener;
import com.shark.base.test.worker.ITestWorker;


public abstract class BaseTestScenario {

	private Gson gson = new Gson();
	protected TestEventListener listener;
	protected String host;
	protected ITestWorker worker;
	protected boolean isDebug = false;

	public BaseTestScenario(TestEventListener listener, ITestWorker worker, boolean isDebug) {
		this.listener = listener;
		this.worker = worker;
		this.isDebug = isDebug;
		this.host = generateHost();
	}

	protected abstract void scenario() throws Exception;

	public void start() throws Exception {
		scenario();
	}

	protected void checkStatusOk(String response) {
		ResponseEntity responseEntity = gson.fromJson(response, ResponseEntity.class);
		int returnCode = responseEntity.getReturnCode();
		if (returnCode == 1) {
			listener.success();
		} else {
			listener.error(responseEntity.getReturnMessage());
		}
	}

	protected void checkStatusError(String response) {
		ResponseEntity responseEntity = gson.fromJson(response, ResponseEntity.class);
		int returnCode = responseEntity.getReturnCode();
		if (returnCode != 1) {
			listener.success();
		} else {
			listener.error("returnCode == 1");
		}
	}
	
	protected abstract String generateHost();
}
