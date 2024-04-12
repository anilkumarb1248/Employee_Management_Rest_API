package com.employee.management.controller;

import com.employee.management.enums.Status;
import com.employee.management.model.Department;
import com.employee.management.response.CustomResponse;
import com.employee.management.response.ListResponse;
import com.employee.management.response.SingleResponse;
import com.employee.management.service.DepartmentService;
import com.employee.management.util.CommonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
@RequestMapping("/department")
public class DepartmentController {

    private static final Logger LOGGER = LoggerFactory.getLogger(DepartmentController.class);

    private final DepartmentService departmentService;

    //@Autowired // @Autowired is not required as we are using single constructor
    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @GetMapping({"/list", "/all"})
    public ResponseEntity<ListResponse<Department>> getDepartments() {
        ListResponse<Department> listResponse = new ListResponse<>();
        List<Department> departmentList = departmentService.getDepartments();
        listResponse.setResponseList(departmentList);
        listResponse.setStatus(Status.SUCCESS);
        listResponse.setStatusResponse(CommonUtil.createStatusResponse(HttpStatus.OK, "Returning department list"));
        return ResponseEntity.ok(listResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SingleResponse<Department>> getDepartment(@PathVariable("id") Long departmentId) {
        SingleResponse<Department> singleResponse = new SingleResponse<>();
        Department department = departmentService.getDepartment(departmentId);
        singleResponse.setResponse(department);
        singleResponse.setStatus(Status.SUCCESS);
        singleResponse.setStatusResponse(CommonUtil.createStatusResponse(HttpStatus.OK, "Returning department details"));
        return ResponseEntity.ok(singleResponse);
    }

    @GetMapping("/name")
    public ResponseEntity<SingleResponse<Department>> getDepartmentByName(@RequestParam("name") String departmentName) {
        SingleResponse<Department> singleResponse = new SingleResponse<>();
        Department department = departmentService.getDepartment(departmentName);
        singleResponse.setResponse(department);
        singleResponse.setStatus(Status.SUCCESS);
        singleResponse.setStatusResponse(CommonUtil.createStatusResponse(HttpStatus.OK, "Returning department details"));
        return ResponseEntity.ok(singleResponse);
    }

    @PostMapping
    public ResponseEntity<CustomResponse> addDepartment(@RequestBody Department department) {
        CustomResponse customResponse = new CustomResponse();
        boolean flag = departmentService.addDepartment(department);
        if (flag) {
            customResponse.setStatus(Status.SUCCESS);
            customResponse.setStatusResponse(CommonUtil.createStatusResponse(HttpStatus.OK, "Department details added successfully"));
        } else {
            customResponse.setStatus(Status.FAILURE);
            customResponse.setErrorResponse(CommonUtil.createErrorResponse(HttpStatus.FAILED_DEPENDENCY, "Failed to add the department details"));
        }
        return ResponseEntity.ok(customResponse);
    }

    @PostMapping("/list")
    public ResponseEntity<CustomResponse> addDepartmentsList(@RequestBody List<Department> departmentList) {
        CustomResponse customResponse = new CustomResponse();
        boolean flag = departmentService.addDepartmentsList(departmentList);
        if (flag) {
            customResponse.setStatus(Status.SUCCESS);
            customResponse.setStatusResponse(CommonUtil.createStatusResponse(HttpStatus.OK, "Department list added successfully"));
        } else {
            customResponse.setStatus(Status.FAILURE);
            customResponse.setErrorResponse(CommonUtil.createErrorResponse(HttpStatus.FAILED_DEPENDENCY, "Failed to add department list"));
        }
        return ResponseEntity.ok(customResponse);
    }

    @PutMapping
    public ResponseEntity<CustomResponse> updateDepartment(@RequestBody Department department) {
        CustomResponse customResponse = new CustomResponse();
        boolean flag = departmentService.updateDepartment(department);
        if (flag) {
            customResponse.setStatus(Status.SUCCESS);
            customResponse.setStatusResponse(CommonUtil.createStatusResponse(HttpStatus.OK, "Department details updated successfully"));
        } else {
            customResponse.setStatus(Status.FAILURE);
            customResponse.setErrorResponse(CommonUtil.createErrorResponse(HttpStatus.FAILED_DEPENDENCY, "Failed to updated department details"));
        }
        return ResponseEntity.ok(customResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CustomResponse> deleteDepartment(@PathVariable("id") Long departmentId) {
        CustomResponse customResponse = new CustomResponse();
        boolean flag = departmentService.deleteDepartment(departmentId);
        if (flag) {
            customResponse.setStatus(Status.SUCCESS);
            customResponse.setStatusResponse(CommonUtil.createStatusResponse(HttpStatus.OK, "Department deleted successfully"));
        } else {
            customResponse.setStatus(Status.FAILURE);
            customResponse.setErrorResponse(CommonUtil.createErrorResponse(HttpStatus.FAILED_DEPENDENCY, "Failed to delete department"));
        }
        return ResponseEntity.ok(customResponse);
    }

    @DeleteMapping("/name")
    public ResponseEntity<CustomResponse> deleteDepartmentByName(@RequestParam("name") String departmentName) {
        CustomResponse customResponse = new CustomResponse();
        boolean flag = departmentService.deleteDepartmentByName(departmentName);
        if (flag) {
            customResponse.setStatus(Status.SUCCESS);
            customResponse.setStatusResponse(CommonUtil.createStatusResponse(HttpStatus.OK, "Department deleted successfully"));
        } else {
            customResponse.setStatus(Status.FAILURE);
            customResponse.setErrorResponse(CommonUtil.createErrorResponse(HttpStatus.FAILED_DEPENDENCY, "Failed to delete department"));
        }
        return ResponseEntity.ok(customResponse);
    }

    @DeleteMapping("/all")
    public ResponseEntity<CustomResponse> deleteAll() {
        CustomResponse customResponse = new CustomResponse();
        boolean flag = departmentService.deleteAll();
        if (flag) {
            customResponse.setStatus(Status.SUCCESS);
            customResponse.setStatusResponse(CommonUtil.createStatusResponse(HttpStatus.OK, "Departments deleted successfully"));
        } else {
            customResponse.setStatus(Status.FAILURE);
            customResponse.setErrorResponse(CommonUtil.createErrorResponse(HttpStatus.FAILED_DEPENDENCY, "Failed to delete departments"));
        }
        return ResponseEntity.ok(customResponse);
    }

    @GetMapping("/data/dummy")
    public ResponseEntity<CustomResponse> addDummyData() {
        CustomResponse customResponse = new CustomResponse();
        boolean flag = departmentService.addDummyData();
        if (flag) {
            customResponse.setStatus(Status.SUCCESS);
            customResponse.setStatusResponse(CommonUtil.createStatusResponse(HttpStatus.OK, "Departments dummy data added successfully"));
        } else {
            customResponse.setStatus(Status.FAILURE);
            customResponse.setErrorResponse(CommonUtil.createErrorResponse(HttpStatus.FAILED_DEPENDENCY, "Failed to add departments dummy data"));
        }
        return ResponseEntity.ok(customResponse);
    }
}
