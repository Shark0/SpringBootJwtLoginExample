package com.shark.base.service;

import com.shark.base.entity.ResponseEntity;
import com.shark.base.util.StringUtil;

import java.util.HashMap;
import java.util.List;

public abstract class BaseStringFromResponseService extends BaseService<HashMap<String, String>, HashMap<String, String>, Void, ResponseEntity> {

    protected abstract List<String> generateCheckKeyList();

    @Override
    protected void checkParameters(HashMap<String, String> parameters) throws Exception{
        List<String> checkKeyList = generateCheckKeyList();
        if(checkKeyList == null || checkKeyList.isEmpty()) {
            return;
        }
        for(String key: checkKeyList) {
            String parameter = parameters.get(key);
            if(StringUtil.isEmpty(parameter)) {
                throw new Exception("Need " + key + " parameter");
            }
        }
    }

    @Override
    protected HashMap<String, String> parseParameters(HashMap<String, String> parameters) {
        return parameters;
    }

    @Override
    protected ResponseEntity generateResultData(Void data) {
        ResponseEntity responseEntity = new ResponseEntity();
        responseEntity.setReturnCode(1);
        return responseEntity;
    }

    @Override
    protected ResponseEntity handleCheckParametersException(Exception e) {
        ResponseEntity responseEntity = new ResponseEntity();
        responseEntity.setReturnCode(-1);
        responseEntity.setReturnMessage(e.getMessage());
        return responseEntity;
    }

    @Override
    protected ResponseEntity handleParseParametersException(Exception e) {
        ResponseEntity responseEntity = new ResponseEntity();
        responseEntity.setReturnCode(-2);
        responseEntity.setReturnMessage(e.getMessage());
        return responseEntity;
    }

    @Override
    protected ResponseEntity handleDataAccessException(Exception e) {
        ResponseEntity responseEntity = new ResponseEntity();
        responseEntity.setReturnCode(-3);
        responseEntity.setReturnMessage(e.getMessage());
        return responseEntity;
    }

    @Override
    protected ResponseEntity handleGenerateResultDataException(Exception e) {
        ResponseEntity responseEntity = new ResponseEntity();
        responseEntity.setReturnCode(-4);
        responseEntity.setReturnMessage(e.getMessage());
        return responseEntity;
    }
}
