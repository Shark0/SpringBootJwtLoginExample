package com.shark.base.util;

import java.util.HashMap;

public class QueryStringUtil {

	public static String generateQueryString(HashMap<String, String> parameters) {
		if (parameters == null || parameters.keySet().size() == 0) {
			return "";
		}
		StringBuilder parameterBuilder = new StringBuilder();
		for (String key : parameters.keySet()) {
			parameterBuilder.append(key);
			parameterBuilder.append("=");
			parameterBuilder.append(parameters.get(key));
			parameterBuilder.append("&");
		}
		String parameter = parameterBuilder.toString();
		parameter = parameter.substring(0, parameter.length() - 1);
		return parameter;
	}
}
