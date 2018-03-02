package com.shark.example;

import com.shark.base.test.factory.HostFactory;
import com.shark.base.test.helper.LoginHelper;
import com.shark.base.test.listener.TestEventListener;
import com.shark.base.test.worker.ITestWorker;
import com.shark.base.test.worker.MockWorker;
import com.shark.example.factory.ExampleHostFactory;
import com.shark.example.scenario.UserIdScenario;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import static junit.framework.TestCase.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
@AutoConfigureMockMvc
public class LoginExampleApplicationTests implements TestEventListener {

	@Autowired
	private MockMvc mockMvc;

	private ITestWorker worker;

	private String authorization;

	@Before
	public void setUp() throws Exception {
		worker = new MockWorker(mockMvc);
		LoginHelper loginHelper = new LoginHelper(mockMvc);
		String host = new ExampleHostFactory().generateHost(HostFactory.Environment.DEV);
		String account = "lin22223344@gmail.com";
		String password = "1qaz2wsx";
		authorization = loginHelper.login(host, account, password);
	}

	@Test
	public void testUserId() throws Exception {
		UserIdScenario scenario = new UserIdScenario(this, worker, true, authorization);
		scenario.start();
	}

	@Override
	public void success() {
		System.out.println("test success");
		assertTrue(true);
	}

	@Override
	public void error(String errorMessage) {
		System.out.println("test error");
		assertTrue(false);
	}
}
