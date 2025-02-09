package com.example.employeemanagement.validation;

import com.example.employeemanagement.model.Employee;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class EmployeeValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return Employee.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Employee employee = (Employee) target;
        
        // Validate fields in declaration order
        validateFullName(employee.getFullName(), errors);
        validateMobileNumber(employee.getMobileNumber(), errors);
        validateEmailId(employee.getEmailId(), errors);
        validateDateOfBirth(employee.getDateOfBirth(), errors);
    }

    private void validateFullName(String fullName, Errors errors) {
        if (fullName == null || fullName.trim().isEmpty()) 
        	errors.rejectValue("fullName", "mandatory", "The fullName is a mandatory field");
        
    }

    private void validateMobileNumber(Long mobileNumber, Errors errors) 
    {
        if (mobileNumber == null) 
            errors.rejectValue("mobileNumber", "mandatory", "The mobileNumber is a mandatory field");
        else if (mobileNumber.toString().length() != 10) 
            errors.rejectValue("mobileNumber", "invalid", "The mobileNumber is a mandatory field");

    }

    private void validateEmailId(String emailId, Errors errors) {
        if (emailId == null || emailId.isEmpty()) 
        	errors.rejectValue("emailId", "mandatory", "The emailId is a mandatory field");
        else if (!emailId.contains("@"))
        	errors.rejectValue("emailId", "invalid", "The emailId should be in a valid email format");
    }

    private void validateDateOfBirth(String dob, Errors errors) {
        if (dob == null) {
            errors.rejectValue("dateOfBirth", "mandatory", "The dateOfBirth is a mandatory field");
            return;
        }
        
        try {
            LocalDate.parse(dob);
        } catch (DateTimeParseException e) {
            errors.rejectValue("dateOfBirth", "invalid", "The dateOfBirth should be in YYYY-MM-DD format");
        }
    }
}
