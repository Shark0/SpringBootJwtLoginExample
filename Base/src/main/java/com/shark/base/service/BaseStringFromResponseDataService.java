package com.shark.base.service;

import com.shark.base.entity.ResponseDataEntity;
import com.shark.base.util.StringUtil;

import java.util.HashMap;
import java.util.List;

public abstract class BaseStringFromResponseDataService<DataAccessObject, ResponseData> extends BaseService<HashMap<String, String>, HashMap<String, String>, DataAccessObject, ResponseDataEntity<ResponseData>> {

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
    protected ResponseDataEntity<ResponseData> handleCheckParametersException(Exception e) {
        ResponseDataEntity<ResponseData> responseDataEntity = new ResponseDataEntity<ResponseData>();
        responseDataEntity.setReturnCode(-1);
        responseDataEntity.setReturnMessage(e.getMessage());
        return responseDataEntity;
    }

    @Override
    protected ResponseDataEntity<ResponseData> handleParseParametersException(Exception e) {
        ResponseDataEntity<ResponseData> responseDataEntity = new ResponseDataEntity<ResponseData>();
        responseDataEntity.setReturnCode(-2);
        responseDataEntity.setReturnMessage(e.getMessage());
        return responseDataEntity;
    }

    @Override
    protected ResponseDataEntity<ResponseData> handleDataAccessException(Exception e) {
        ResponseDataEntity<ResponseData> responseDataEntity = new ResponseDataEntity<ResponseData>();
        responseDataEntity.setReturnCode(-3);
        responseDataEntity.setReturnMessage(e.getMessage());
        return responseDataEntity;
    }

    @Override
    protected ResponseDataEntity<ResponseData> handleGenerateResultDataException(Exception e) {
        ResponseDataEntity<ResponseData> responseDataEntity = new ResponseDataEntity<ResponseData>();
        responseDataEntity.setReturnCode(-4);
        responseDataEntity.setReturnMessage(e.getMessage());
        return responseDataEntity;
    }
}
