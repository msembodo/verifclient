package com.idemia.jkt.tec.VerifClient.response;

import com.google.gson.Gson;

public class ConverterResponse {

    private boolean convertSuccess;
    private String generatedCsv;
    private String message;

    private ConverterResponse() {}

    public ConverterResponse(boolean convertSuccess, String resultCsv, String message) {
        this.convertSuccess = convertSuccess;
        this.generatedCsv = resultCsv;
        this.message = message;
    }

    public boolean isConvertSuccess() {
        return convertSuccess;
    }

    public void setConvertSuccess(boolean convertSuccess) {
        this.convertSuccess = convertSuccess;
    }

    public String getGeneratedCsv() {
        return generatedCsv;
    }

    public void setGeneratedCsv(String generatedCsv) {
        this.generatedCsv = generatedCsv;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    // for debug
    public String toJson() {
        return new Gson().toJson(this);
    }
}
