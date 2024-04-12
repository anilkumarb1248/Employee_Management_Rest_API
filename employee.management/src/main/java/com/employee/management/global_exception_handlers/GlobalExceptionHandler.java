package com.employee.management.global_exception_handlers;

import com.employee.management.enums.Status;
import com.employee.management.exceptions.ApplicationInternalException;
import com.employee.management.exceptions.DuplicateEmployeeException;
import com.employee.management.exceptions.EmployeeNotFoundException;
import com.employee.management.response.CustomResponse;
import com.employee.management.util.CommonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpServerErrorException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    // Added in separate class not picking those handlers
    @ExceptionHandler(value = { DuplicateEmployeeException.class })
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<CustomResponse> handleDuplicateEmployeeException(DuplicateEmployeeException ex) {
        CustomResponse customResponse = CommonUtil.createCustomErrorResponse(Status.FAILURE, HttpStatus.CONFLICT, ex.getMessage());
        return ResponseEntity.ok(customResponse);
    }

    @ExceptionHandler(value = { EmployeeNotFoundException.class })
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<CustomResponse> handleEmployeeNotFoundException(EmployeeNotFoundException ex) {
        CustomResponse customResponse = CommonUtil.createCustomErrorResponse(Status.FAILURE, HttpStatus.NOT_FOUND, ex.getMessage());
        return ResponseEntity.ok(customResponse);
    }

    @ExceptionHandler(value = { ApplicationInternalException.class })
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<CustomResponse> handleApplicationInternalException(ApplicationInternalException ex) {
        CustomResponse customResponse = CommonUtil.createCustomErrorResponse(Status.FAILURE, HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        return ResponseEntity.ok(customResponse);
    }

    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<CustomResponse> handleException(Exception ex) {
        CustomResponse customResponse = CommonUtil.createCustomErrorResponse(Status.FAILURE, HttpStatus.BAD_REQUEST, ex.getMessage());
        return ResponseEntity.ok(customResponse);
    }

    // Checking - Can we handle INTERNAL_SERVER_ERROR? I think not
    @ExceptionHandler(value = HttpServerErrorException.InternalServerError.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<CustomResponse> handleServerError(HttpServerErrorException.InternalServerError ex) {
        CustomResponse customResponse = CommonUtil.createCustomErrorResponse(Status.FAILURE, HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        return ResponseEntity.ok(customResponse);
    }

}