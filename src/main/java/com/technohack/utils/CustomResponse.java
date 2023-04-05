package com.technohack.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomResponse {
    private int statusCode;
    private String message;
    private Object data;

    public static CustomResponse buildErrorResponse(String message,int... statusCode) {
        return new CustomResponse(500, message, null);
    }
    public static CustomResponse buildSuccessResponse(String message, Object data) {
        return new CustomResponse(200, message, data);
    }
}
