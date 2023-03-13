package com.example.loanlending.Models;

public class ResponseBody {
    private Integer responseCode;
    private String responseDescription;

    public ResponseBody() {
    }

    public Integer getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(Integer responseCode) {
        this.responseCode = responseCode;
    }

    public String getresponseDescription() {
        return responseDescription;
    }

    public void setresponseDescription(String responseBody) {
        this.responseDescription = responseBody;
    }

    @Override
    public String toString() {
        return "ResponseBodey{" +
                "responseCode=" + responseCode +
                ", responseDescription='" + responseDescription + '\'' +
                '}';
    }
}
