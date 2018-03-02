package com.shark.example.scenario;

import com.shark.base.test.listener.TestEventListener;
import com.shark.base.test.worker.ITestWorker;
import com.shark.base.test.worker.ResponseResultEntity;

import java.util.HashMap;

public class UserIdScenario extends BaseExampleScenario {

    public UserIdScenario(TestEventListener listener, ITestWorker worker, boolean isDebug, String authorization) {
        super(listener, worker, isDebug, authorization);
    }

    @Override
    protected void scenario() throws Exception {
        String apiPath = "/userId";
        HashMap<String, String> headerHashMap = new HashMap();
        headerHashMap.put("Content-Type", "application/x-www-form-urlencoded");
        headerHashMap.put("charset", "utf-8");
        headerHashMap.put(HEADER_AUTHORIZATION, authorization);

        HashMap<String, String> postBody = new HashMap();


        ResponseResultEntity result = worker.get(host, apiPath, headerHashMap, "", isDebug);
        String responseContent = result.getContent();
        checkStatusOk(responseContent);
    }
}
