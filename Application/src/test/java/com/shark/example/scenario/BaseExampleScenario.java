package com.shark.example.scenario;

import com.shark.base.test.factory.HostFactory;
import com.shark.base.test.listener.TestEventListener;
import com.shark.base.test.scenario.BaseTestScenario;
import com.shark.base.test.worker.ITestWorker;
import com.shark.example.factory.ExampleHostFactory;

public abstract class BaseExampleScenario extends BaseTestScenario {

    protected final String HEADER_AUTHORIZATION = "Authorization";

    protected String authorization;

    public BaseExampleScenario(TestEventListener listener, ITestWorker worker, boolean isDebug, String authorization) {
        super(listener, worker, isDebug);
        this.authorization = authorization;
    }

    @Override
    protected String generateHost() {
        return new ExampleHostFactory().generateHost(HostFactory.Environment.DEV);
    }
}
