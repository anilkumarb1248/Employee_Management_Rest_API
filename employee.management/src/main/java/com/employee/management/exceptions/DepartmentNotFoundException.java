package com.employee.management.exceptions;

public class DepartmentNotFoundException extends RuntimeException {
    public DepartmentNotFoundException(String msg) {
        super(msg);
    }
}