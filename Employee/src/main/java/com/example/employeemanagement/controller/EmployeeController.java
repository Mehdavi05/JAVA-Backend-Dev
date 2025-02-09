package com.example.employeemanagement.controller;

import com.example.employeemanagement.model.Employee;
import com.example.employeemanagement.validation.EmployeeValidator;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

@RestController
public class EmployeeController {

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.setValidator(new EmployeeValidator());
    }

    @PostMapping("/employee")
    public ResponseEntity<?> createEmployee(@RequestBody Employee employee, BindingResult bindingResult) {
        new EmployeeValidator().validate(employee, bindingResult);
        
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }
        return ResponseEntity.ok().build();
    }
}