package com.employee.management.global_exception_handlers;

import com.employee.management.enums.Status;
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

@RestControllerAdvice
public class EmployeeExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeExceptionHandler.class);

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

}
