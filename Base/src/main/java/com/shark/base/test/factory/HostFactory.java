package com.shark.base.test.factory;

public abstract class HostFactory {

	public static enum Environment {DEV, TEST, PRD};
		
	public abstract String generateHost(Environment environment);
	
}
