package com.shark.base.util;

import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;

public class ServletUtil {

    public static HashMap<String, String> generateServiceParameters(HttpServletRequest request, String... keys) {
        HashMap<String, String> parameters = new HashMap<String, String>();
        for(String key: keys) {
            parameters.put(key, request.getParameter(key));
        }
        return parameters;
    }
}
