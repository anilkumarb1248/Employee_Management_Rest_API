package com.employee.management.enums;

public enum MaritalStatus {

    MARRIED("Married"), UNMARRIED("Un Married");

    private String status;

    private MaritalStatus(String status) {
        this.status = status;
    }

    public String getCode() {
        return status;
    }
}