package com.employee.management.controller;

import com.employee.management.enums.Status;
import com.employee.management.model.Employee;
import com.employee.management.request.PaginationRequest;
import com.employee.management.response.CustomResponse;
import com.employee.management.response.ListResponse;
import com.employee.management.response.SingleResponse;
import com.employee.management.service.EmployeeService;
import com.employee.management.util.CommonUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeController.class);

    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping({"/list", "/all"})
    public ResponseEntity<ListResponse<Employee>> getEmployeeList() {
        LOGGER.info("Request came to fetch all the employees details");
        ListResponse<Employee> listResponse = new ListResponse<>();
        List<Employee> employeeList = employeeService.getEmployeeList();
        if (CollectionUtils.isNotEmpty(employeeList)) {
            listResponse.setResponseList(employeeList);
            listResponse.setStatus(Status.SUCCESS);
            listResponse.setStatusResponse(CommonUtil.createStatusResponse(HttpStatus.OK, "Returning the employees list"));
            LOGGER.info("Returning the employees list, size: {}", employeeList.size());
            return ResponseEntity.ok(listResponse);
        } else {
            listResponse.setStatus(Status.FAILURE);
            listResponse.setErrorResponse(CommonUtil.createErrorResponse(HttpStatus.NOT_FOUND, "No employees found"));
            LOGGER.info("No employees found");
            return new ResponseEntity<>(listResponse, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/list/pagination")
    public ResponseEntity<ListResponse<Employee>> getEmployeesByPagination(@RequestBody PaginationRequest paginationRequest) {
        LOGGER.info("Request came to fetch the employees details with pagination, pagination: ", paginationRequest);
        ListResponse<Employee> listResponse = new ListResponse<>();
        List<Employee> employeeList = employeeService.getEmployeesByPagination(paginationRequest);
        if (CollectionUtils.isNotEmpty(employeeList)) {
            listResponse.setResponseList(employeeList);
            listResponse.setStatus(Status.SUCCESS);
            listResponse.setStatusResponse(CommonUtil.createStatusResponse(HttpStatus.OK, "Returning the employees list with pagination"));
            LOGGER.info("Returning the employees list with pagination, size: {}", employeeList.size());
            return ResponseEntity.ok(listResponse);
        } else {
            listResponse.setStatus(Status.FAILURE);
            listResponse.setErrorResponse(CommonUtil.createErrorResponse(HttpStatus.NOT_FOUND, "No employees found with pagination"));
            LOGGER.info("No employees found with pagination");
            return new ResponseEntity<>(listResponse, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<SingleResponse<Employee>> getEmployee(@PathVariable(value = "id") Integer employeeId) {
        LOGGER.info("Request came to fetch the employee details with id: {}", employeeId);
        SingleResponse<Employee> singleResponse = new SingleResponse<>();
        Employee employee = employeeService.getEmployee(employeeId);
        singleResponse.setResponse(employee);
        singleResponse.setStatus(Status.SUCCESS);
        singleResponse.setStatusResponse(CommonUtil.createStatusResponse(HttpStatus.OK, "Returning the employee"));
        LOGGER.info("Returning the employee details with id: {}, response: ", employeeId, employee);
        return ResponseEntity.ok(singleResponse);
    }

    @GetMapping("/username")
    public ResponseEntity<SingleResponse<Employee>> getEmployeeByName(@RequestParam("username") String userName) {
        LOGGER.info("Request came to fetch the employee details with username: {}", userName);
        SingleResponse<Employee> singleResponse = new SingleResponse<>();
        if (StringUtils.isEmpty(userName)) {
            singleResponse.setStatus(Status.FAILURE);
            singleResponse.setErrorResponse(CommonUtil.createErrorResponse(HttpStatus.BAD_REQUEST, "Username should not be empty"));
            return new ResponseEntity(singleResponse, HttpStatus.BAD_REQUEST);
        }
        Employee employee = employeeService.getEmployeeByName(userName);
        singleResponse.setResponse(employee);
        singleResponse.setStatus(Status.SUCCESS);
        singleResponse.setStatusResponse(CommonUtil.createStatusResponse(HttpStatus.OK, "Returning the employee"));
        LOGGER.info("Returning the employee details with username: {}, response: ", userName, employee);
        return ResponseEntity.ok(singleResponse);
    }

    @PostMapping
    public ResponseEntity<CustomResponse> addEmployee(@RequestBody Employee employee) {
        LOGGER.info("Request came to add the employee, request: ", employee);
        CustomResponse customResponse = new CustomResponse();
        Integer newId = employeeService.addEmployee(employee);
        if (newId > 0) {
            LOGGER.info("Successfully added the employee");
            customResponse.setStatus(Status.SUCCESS);
            customResponse.setStatusResponse(CommonUtil.createStatusResponse(HttpStatus.OK, "Employee added successfully"));
            return ResponseEntity.ok(customResponse);
        } else {
            LOGGER.warn("Failed to add the employee");
            customResponse.setStatus(Status.FAILURE);
            customResponse.setErrorResponse(CommonUtil.createErrorResponse(HttpStatus.FAILED_DEPENDENCY, "Failed to add the employee"));
            return new ResponseEntity<>(customResponse, HttpStatus.FAILED_DEPENDENCY);
        }
    }

    @PostMapping("/list")
    public ResponseEntity<CustomResponse> addEmployees(@RequestBody List<Employee> employees) {
        LOGGER.info("Request came to add the employees list, size: {}", employees.size());
        CustomResponse customResponse = new CustomResponse();
        if(CollectionUtils.isEmpty(employees)){
            LOGGER.info("Employees list is empty");
            customResponse.setStatus(Status.FAILURE);
            customResponse.setErrorResponse(CommonUtil.createErrorResponse(HttpStatus.BAD_REQUEST, "Employees list is empty"));
            return new ResponseEntity(customResponse, HttpStatus.BAD_REQUEST);
        }

        boolean flag = employeeService.addEmployees(employees);
        if (flag) {
            LOGGER.info("Successfully added the employees list");
            customResponse.setStatus(Status.SUCCESS);
            customResponse.setStatusResponse(CommonUtil.createStatusResponse(HttpStatus.OK, "Employees added successfully"));
            return ResponseEntity.ok(customResponse);
        } else {
            LOGGER.warn("Failed to add the employees list");
            customResponse.setStatus(Status.FAILURE);
            customResponse.setErrorResponse(CommonUtil.createErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to add the employees"));
            return new ResponseEntity<>(customResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping
    public ResponseEntity<CustomResponse> updateEmployee(@RequestBody Employee employee) {
        LOGGER.info("Request came to update the employee details, username: {}, empId: {}", employee.getUserName(), employee.getEmpId());
        CustomResponse customResponse = new CustomResponse();
        boolean flag = employeeService.updateEmployee(employee);
        if(employee == null || StringUtils.isEmpty(employee.getUserName())){
            LOGGER.info("Username is empty");
            customResponse.setStatus(Status.FAILURE);
            customResponse.setErrorResponse(CommonUtil.createErrorResponse(HttpStatus.BAD_REQUEST, "Username should not be empty"));
            return new ResponseEntity(customResponse, HttpStatus.BAD_REQUEST);
        }
        if (flag) {
            LOGGER.info("Successfully updated the employee details");
            customResponse.setStatus(Status.SUCCESS);
            customResponse.setStatusResponse(CommonUtil.createStatusResponse(HttpStatus.OK, "Employee updated successfully"));
            return ResponseEntity.ok(customResponse);
        } else {
            LOGGER.warn("Failed to update the employee details");
            customResponse.setStatus(Status.FAILURE);
            customResponse.setErrorResponse(CommonUtil.createErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to update the employee"));
            return new ResponseEntity<>(customResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CustomResponse> deleteEmployee(@PathVariable(value = "id") Integer employeeId) {
        LOGGER.info("Request came to delete the employee with id: {}", employeeId);
        CustomResponse customResponse = new CustomResponse();
        boolean flag = employeeService.deleteEmployee(employeeId);
        if (flag) {
            LOGGER.info("Successfully deleted the employee");
            customResponse.setStatus(Status.SUCCESS);
            customResponse.setStatusResponse(CommonUtil.createStatusResponse(HttpStatus.OK, "Employee deleted successfully"));
            return ResponseEntity.ok(customResponse);
        } else {
            LOGGER.warn("Failed to delete the employee");
            customResponse.setStatus(Status.FAILURE);
            customResponse.setErrorResponse(CommonUtil.createErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to delete the employee"));
            return new ResponseEntity<>(customResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @DeleteMapping("/username")
    public ResponseEntity<CustomResponse> deleteEmployeeByUserName(@RequestParam("username") String userName) {
        LOGGER.info("Request came to delete the employee with username: {}", userName);
        CustomResponse customResponse = new CustomResponse();
        if (StringUtils.isEmpty(userName)) {
            LOGGER.info("Username is empty");
            customResponse.setStatus(Status.FAILURE);
            customResponse.setErrorResponse(CommonUtil.createErrorResponse(HttpStatus.BAD_REQUEST, "Username should not be empty"));
            return new ResponseEntity(customResponse, HttpStatus.BAD_REQUEST);
        }
        boolean flag  = employeeService.deleteEmployeeByUserName(userName);
        if (flag) {
            LOGGER.info("Successfully deleted the employee");
            customResponse.setStatus(Status.SUCCESS);
            customResponse.setStatusResponse(CommonUtil.createStatusResponse(HttpStatus.OK, "Employee deleted successfully"));
            return ResponseEntity.ok(customResponse);
        } else {
            LOGGER.warn("Failed to delete the employee");
            customResponse.setStatus(Status.FAILURE);
            customResponse.setErrorResponse(CommonUtil.createErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to delete the employee"));
            return new ResponseEntity<>(customResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @DeleteMapping("/all")
    public ResponseEntity<CustomResponse> deleteAll() {
        LOGGER.info("Request came to delete all the employees details");
        CustomResponse customResponse = new CustomResponse();
        boolean flag = employeeService.deleteAll();
        if (flag) {
            LOGGER.info("Successfully deleted all the employees");
            customResponse.setStatus(Status.SUCCESS);
            customResponse.setStatusResponse(CommonUtil.createStatusResponse(HttpStatus.OK, "Employees deleted successfully"));
            return ResponseEntity.ok(customResponse);
        } else {
            LOGGER.warn("Failed to delete all the employees");
            customResponse.setStatus(Status.FAILURE);
            customResponse.setErrorResponse(CommonUtil.createErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to delete the employees"));
            return new ResponseEntity<>(customResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


    @GetMapping("/data/dummy")
    public ResponseEntity<CustomResponse> addDummyData() {
        LOGGER.info("Request came to add the dummy data from the employee json file");
        CustomResponse customResponse = new CustomResponse();
        boolean flag = employeeService.addDummyData();
        if (flag) {
            LOGGER.info("Successfully added the dummy employees");
            customResponse.setStatus(Status.SUCCESS);
            customResponse.setStatusResponse(CommonUtil.createStatusResponse(HttpStatus.OK, "Dummy Employees inserted successfully"));
            return ResponseEntity.ok(customResponse);
        } else {
            LOGGER.warn("Failed to add the dummy employees");
            customResponse.setStatus(Status.FAILURE);
            customResponse.setErrorResponse(CommonUtil.createErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to insert the dummy employees"));
            return new ResponseEntity<>(customResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
