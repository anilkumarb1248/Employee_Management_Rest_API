package com.employee.management.exceptions;

public class DuplicateDepartmentException extends RuntimeException {

    public DuplicateDepartmentException(String msg) {
        super(msg);
    }
}