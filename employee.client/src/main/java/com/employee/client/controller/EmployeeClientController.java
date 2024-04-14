package com.employee.client.controller;

import com.employee.client.service.OperationsPerformerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/client")
public class EmployeeClientController {

    private final OperationsPerformerService service;

    @Autowired
    public EmployeeClientController(OperationsPerformerService operationsPerformerService) {
        this.service = operationsPerformerService;
    }

    @GetMapping("/insertDummyData")
    @ResponseStatus(HttpStatus.OK)
    public void insertDummyData() {
        service.insertDummyData();
    }

    @GetMapping("/addEmployee")
    @ResponseStatus(HttpStatus.OK)
    public void addEmployee() {
        service.addEmployee();
    }

    @GetMapping("/addEmployeesList")
    @ResponseStatus(HttpStatus.OK)
    public void addEmployeesList() {
        service.addEmployeesList();
    }

    @GetMapping("/updateEmployee")
    @ResponseStatus(HttpStatus.OK)
    public void updateEmployee() {
        service.updateEmployee();
    }

    @GetMapping("/getAll")
    @ResponseStatus(HttpStatus.OK)
    public void getAll() {
        service.getAll();
    }

    @GetMapping("/getWithPagination")
    @ResponseStatus(HttpStatus.OK)
    public void getWithPagination() {
        service.getWithPagination();
    }

    @GetMapping("/getById")
    @ResponseStatus(HttpStatus.OK)
    public void getById() {
        service.getById();
    }

    @GetMapping("/getByUsername")
    @ResponseStatus(HttpStatus.OK)
    public void getByUsername() {
        service.getByUsername();
    }

    @GetMapping("/deleteById")
    @ResponseStatus(HttpStatus.OK)
    public void deleteById() {
        service.deleteById();
    }

    @GetMapping("/deleteByUsername")
    @ResponseStatus(HttpStatus.OK)
    public void deleteByUsername() {
        service.deleteByUsername();
    }

    @GetMapping("/deleteAll")
    @ResponseStatus(HttpStatus.OK)
    public void deleteAll() {
        service.deleteAll();
    }


}
