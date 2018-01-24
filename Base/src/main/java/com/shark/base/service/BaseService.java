package com.shark.base.service;

public abstract class BaseService<Parameters, DataAccessParameters, DataAccessObject, Result> {

    public Result request(Parameters parameters) {
        try {
            checkParameters(parameters);
        } catch (Exception e) {
            e.printStackTrace();
            return handleCheckParametersException(e);
        }

        DataAccessParameters dataAccessParameters = null;
        try {
            dataAccessParameters = parseParameters(parameters);
        } catch (Exception e) {
            e.printStackTrace();
            return handleParseParametersException(e);
        }

        DataAccessObject dataAccessObject = null;
        try {
            dataAccessObject = dataAccess(dataAccessParameters);
        } catch (Exception e) {
            e.printStackTrace();
            return handleDataAccessException(e);
        }

        Result result = null;
        try {
            result = generateResultData(dataAccessObject);
        } catch (Exception e) {
            e.printStackTrace();
            return handleGenerateResultDataException(e);
        }

        return result;
    }


    protected abstract void checkParameters(Parameters parameters) throws Exception;

    protected abstract DataAccessParameters parseParameters(Parameters parameters) throws Exception;

    protected abstract DataAccessObject dataAccess(DataAccessParameters parameters) throws Exception;

    protected abstract Result generateResultData(DataAccessObject dataAccessObject) throws Exception;

    protected abstract Result handleCheckParametersException(Exception e);

    protected abstract Result handleParseParametersException(Exception e);

    protected abstract Result handleDataAccessException(Exception e);

    protected abstract Result handleGenerateResultDataException(Exception e);
}
