package com.employee.client.enums;

public enum Role {
    CEO("CEO"),
    PRODUCT_MANAGER("Product Manager"),
    DELIVERY_MANAGER("Delivery Manager"),
    TECHNICAL_MANAGER("Technical Manager"),
    SCRUM_MASTER("Scrum Master"),
    LEAD("Lead"),
    TA("Technology Analyst"),
    SSE("Senior Software Engineer"),
    SE("Software Engineer"),
    ACCOUNTANT("Accountant"),
    SG("Security Guard");


    private String role;

    private Role(String role) {
        this.role = role;
    }

    public String getCode() {
        return role;
    }
}