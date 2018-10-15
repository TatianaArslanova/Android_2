package com.example.ama.android2_lesson06_2.model;

public class DeviceParam {
    private String paramName;
    private String paramValue;

    public DeviceParam(String paramName, String paramValue) {
        this.paramName = paramName;
        this.paramValue = paramValue;
    }

    public String getParamName() {
        return paramName;
    }

    public String getParamValue() {
        return paramValue;
    }
}
