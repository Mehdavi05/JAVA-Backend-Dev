package com.example.employeemanagement.validation;

public class FieldValidationMessage {
    private String field;
    private String message;

    public FieldValidationMessage(String field, String message) {
        this.field = field;
        this.message = message;
    }

    // Getters
    public String getField() { return field; }
    public String getMessage() { return message; }
}
