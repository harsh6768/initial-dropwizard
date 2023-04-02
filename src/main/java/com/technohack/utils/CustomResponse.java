package com.technohack.utils;

public class CustomResponse {
    private int statusCode;
    private String message;
    private Object data;

    public CustomResponse() {}

    public CustomResponse(int statusCode, String message, Object data) {
        this.statusCode = statusCode;
        this.message = message;
        this.data = data;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public static CustomResponse buildErrorResponse(String message,int... statusCode) {
        return new CustomResponse(500, message, null);
    }
    public static CustomResponse buildSuccessResponse(String message, Object data) {
        return new CustomResponse(200, message, data);
    }
}
