package com.employee.management.util;

import com.employee.management.enums.Status;
import com.employee.management.response.CustomResponse;
import com.employee.management.response.ErrorResponse;
import com.employee.management.response.StatusResponse;
import org.springframework.http.HttpStatus;

public class CommonUtil {

    public static CustomResponse createCustomResponse(Status status, StatusResponse statusResponse){
        return new CustomResponse(status, statusResponse);
    }

    public static CustomResponse createCustomResponse(Status status, ErrorResponse errorResponse){
        return new CustomResponse(status, errorResponse);
    }

    public static CustomResponse createCustomStatusResponse(Status status, HttpStatus httpStatus, String message){
        return  createCustomResponse(status, createStatusResponse(httpStatus, message));
    }

    public static CustomResponse createCustomErrorResponse(Status status, HttpStatus httpStatus, String errorMessage){
        return createCustomResponse(status, createErrorResponse(httpStatus, errorMessage));
    }

    public static StatusResponse createStatusResponse(HttpStatus httpStatus, String message) {
        return  new StatusResponse(String.valueOf(httpStatus.value()), message);
    }

    public static ErrorResponse createErrorResponse(HttpStatus httpStatus, String errorMessage){
        return new ErrorResponse(String.valueOf(httpStatus.value()), errorMessage);
    }
}
