package com.employee.client.service;


import com.employee.client.caller.EmployeeServiceClient;
import com.employee.client.config.EmployeeServiceEndpointsConfig;
import com.employee.client.enums.SortOrder;
import com.employee.client.enums.Status;
import com.employee.client.model.Employee;
import com.employee.client.reader.EmployeeJsonDataReader;
import com.employee.client.request.PaginationRequest;
import com.employee.client.response.CustomResponse;
import com.employee.client.response.ListResponse;
import com.employee.client.response.SingleResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@Component
public class OperationsPerformerService {

    private final Logger LOGGER = LoggerFactory.getLogger(OperationsPerformerService.class);

    private final EmployeeServiceClient client;
    private final EmployeeServiceEndpointsConfig endpoints;
    private final EmployeeJsonDataReader reader;

    @Autowired
    public OperationsPerformerService(EmployeeServiceClient employeeServiceClient,
                                      EmployeeServiceEndpointsConfig employeeServiceEndpointsConfig,
                                      EmployeeJsonDataReader employeeJsonDataReader) {
        this.client = employeeServiceClient;
        this.endpoints = employeeServiceEndpointsConfig;
        this.reader = employeeJsonDataReader;
    }

    public void insertDummyData() {
        LOGGER.info("Performing inserting the dummy data operation - Started");
        try {
            ResponseEntity<CustomResponse> entity = client.callEmployeeManagementService(endpoints.getInsertDummyDataURL(), HttpMethod.GET,CustomResponse.class);
            if(HttpStatus.OK ==  entity.getStatusCode() && entity.getBody() != null){
                CustomResponse customResponse = entity.getBody();
                if(Status.SUCCESS == customResponse.getStatus()){
                    LOGGER.info("Successfully inserted the dummy data, message: {}",
                            customResponse.getStatusResponse() != null ? customResponse.getStatusResponse().getStatusMessage() : "");

                } else {
                    LOGGER.info("Got failure response from employee management service while inserting the dummy data");
                    if(customResponse.getErrorResponse() != null){
                        LOGGER.info("Failed with code: {}, message: {} ",
                                customResponse.getErrorResponse().getErrorCode(),
                                customResponse.getErrorResponse().getErrorMessage());
                    }
                }
            }
        } catch (RestClientException rce){
            LOGGER.error("RestClientException occurred while inserting the dummy data, exception: {}", rce.getMessage());
        } catch (Exception e){
            LOGGER.error("Exception occurred while inserting the dummy data, exception: {}", e.getMessage());
        }
        LOGGER.info("Performing inserting the dummy data operation - Ended");
    }

    public void addEmployee() {
        LOGGER.info("Performing adding the employee operation - Started");
        try {
            Employee employee = reader.getSingleEmployeeData();
            ResponseEntity<CustomResponse> entity = client.callEmployeeManagementService(endpoints.getAddEmployeeURL(), HttpMethod.POST, employee,CustomResponse.class);
            if(HttpStatus.OK ==  entity.getStatusCode() && entity.getBody() != null){
                CustomResponse customResponse = entity.getBody();
                if(Status.SUCCESS == customResponse.getStatus()){
                    LOGGER.info("Successfully added the employee, message: {}",
                            customResponse.getStatusResponse() != null ? customResponse.getStatusResponse().getStatusMessage() : "");
                } else {
                    LOGGER.info("Got failure response from employee management service while adding the employee");
                    if(customResponse.getErrorResponse() != null){
                        LOGGER.info("Failed with code: {}, message: {} ",
                                customResponse.getErrorResponse().getErrorCode(),
                                customResponse.getErrorResponse().getErrorMessage());
                    }
                }
            }
        } catch (RestClientException rce){
            LOGGER.error("RestClientException occurred while adding the employee, exception: {}", rce.getMessage());
        } catch (Exception e){
            LOGGER.error("Exception occurred while adding the employee: {}", e.getMessage());
        }
        LOGGER.info("Performing adding the employee operation - Ended");
    }


    public void addEmployeesList() {
        LOGGER.info("Performing adding the employees list operation - Started");
        try {
            List<Employee> employeeList = reader.getEmployeeListData();
            ResponseEntity<CustomResponse> entity = client.callEmployeeManagementService(endpoints.getAddEmployeesListURL(), HttpMethod.POST, employeeList, CustomResponse.class);
            if(HttpStatus.OK ==  entity.getStatusCode() && entity.getBody() != null){
                CustomResponse customResponse = entity.getBody();
                if(Status.SUCCESS == customResponse.getStatus()){
                    LOGGER.info("Successfully added the employees list, message: {}",
                            customResponse.getStatusResponse() != null ? customResponse.getStatusResponse().getStatusMessage() : "");

                } else {
                    LOGGER.info("Got failure response from employee management service while adding the employees list");
                    if(customResponse.getErrorResponse() != null){
                        LOGGER.info("Failed with code: {}, message: {} ",
                                customResponse.getErrorResponse().getErrorCode(),
                                customResponse.getErrorResponse().getErrorMessage());
                    }
                }
            }
        } catch (RestClientException rce){
            LOGGER.error("RestClientException occurred while adding the employees list data, exception: {}", rce.getMessage());
        } catch (Exception e){
            LOGGER.error("Exception occurred while adding the employees list data, exception: {}", e.getMessage());
        }
        LOGGER.info("Performing adding the employees list operation - Ended");
    }

    public void updateEmployee() {
        LOGGER.info("Performing the update employee operation - Started");
        try {
            Employee employee = reader.getUpdateEmployeeData();
            ResponseEntity<CustomResponse> entity = client.callEmployeeManagementService(endpoints.getUpdateEmployeeURL(), HttpMethod.PUT, employee,CustomResponse.class);
            if(HttpStatus.OK ==  entity.getStatusCode() && entity.getBody() != null){
                CustomResponse customResponse = entity.getBody();
                if(Status.SUCCESS == customResponse.getStatus()){
                    LOGGER.info("Successfully updated the employee details, message: {}",
                            customResponse.getStatusResponse() != null ? customResponse.getStatusResponse().getStatusMessage() : "");

                } else {
                    LOGGER.info("Got failure response from employee management service while updating the employee");
                    if(customResponse.getErrorResponse() != null){
                        LOGGER.info("Failed with code: {}, message: {} ",
                                customResponse.getErrorResponse().getErrorCode(),
                                customResponse.getErrorResponse().getErrorMessage());
                    }
                }
            }
        } catch (RestClientException rce){
            LOGGER.error("RestClientException occurred while updating the employee, exception: {}", rce.getMessage());
        } catch (Exception e){
            LOGGER.error("Exception occurred while updating the employee, exception: {}", e.getMessage());
        }
        LOGGER.info("Performing the update employee operation - Ended");
    }

    public void getAll() {
        LOGGER.info("Performing the fetch all employees operation - Started");
        try {
            ResponseEntity<ListResponse<Employee>> entity = client.callEmployeeManagementService(endpoints.getGetAllURL(), HttpMethod.GET, new ParameterizedTypeReference<ListResponse<Employee>>() {});
            if(HttpStatus.OK ==  entity.getStatusCode() && entity.getBody() != null){
                ListResponse<Employee> listResponse = entity.getBody();
                if(Status.SUCCESS == listResponse.getStatus()){
                    LOGGER.info("Successfully fetched the all employees data, message: {}",
                            listResponse.getStatusResponse() != null ? listResponse.getStatusResponse().getStatusMessage() : "");
                    LOGGER.info("Employees list size: {} ", listResponse.getResponseList().size());
                } else {
                    LOGGER.info("Got failure response from employee management service while fetching all employees list");
                    if(listResponse.getErrorResponse() != null){
                        LOGGER.info("Failed with code: {}, message: {} ",
                                listResponse.getErrorResponse().getErrorCode(),
                                listResponse.getErrorResponse().getErrorMessage());
                    }
                }
            }
        } catch (RestClientException rce){
            LOGGER.error("RestClientException occurred while fetching all employees, exception: {}", rce.getMessage());
        } catch (Exception e){
            LOGGER.error("Exception occurred while fetching all employees, exception: {}", e.getMessage());
        }
        LOGGER.info("Performing the fetch all employees operation - Ended");
    }

    public void getWithPagination() {
        LOGGER.info("Performing the fetch employees with pagination operation - Started");
        try {
            PaginationRequest paginationRequest = new PaginationRequest();
            paginationRequest.setPageNumber(0);
            paginationRequest.setPageSize(10);
            paginationRequest.setSortingBy("firstName");
            paginationRequest.setSortOrder(SortOrder.ASC);

            ResponseEntity<ListResponse<Employee>> entity = client.callEmployeeManagementService(endpoints.getGetWithPaginationURL(), HttpMethod.POST, paginationRequest, new ParameterizedTypeReference<ListResponse<Employee>>() {});
            if(HttpStatus.OK ==  entity.getStatusCode() && entity.getBody() != null){
                ListResponse<Employee> listResponse = entity.getBody();
                if(Status.SUCCESS == listResponse.getStatus()){
                    LOGGER.info("Successfully fetched the employees with pagination, message: {}",
                            listResponse.getStatusResponse() != null ? listResponse.getStatusResponse().getStatusMessage() : "");
                    LOGGER.info("Employees list size with pagination: {} ", listResponse.getResponseList().size());
                } else {
                    LOGGER.info("Got failure response from employee management service while fetching employees with pagination");
                    if(listResponse.getErrorResponse() != null){
                        LOGGER.info("Failed with code: {}, message: {} ",
                                listResponse.getErrorResponse().getErrorCode(),
                                listResponse.getErrorResponse().getErrorMessage());
                    }
                }
            }
        } catch (RestClientException rce){
            LOGGER.error("RestClientException occurred while fetching employees with pagination, exception: {}", rce.getMessage());
        } catch (Exception e){
            LOGGER.error("Exception occurred while employees with pagination, exception: {}", e.getMessage());
        }
        LOGGER.info("Performing the fetch employees with pagination operation - Ended");
    }

    public void getById() {
        LOGGER.info("Performing fetching the employee with id operation - Started");
        Integer employeeId = 5;
        String path = endpoints.getGetByIdURL();
        String resourcePath = UriComponentsBuilder.fromUriString(path).buildAndExpand(employeeId).toString();
        try {
            ResponseEntity<SingleResponse<Employee>> entity = client.callEmployeeManagementService(resourcePath, HttpMethod.GET, new ParameterizedTypeReference<SingleResponse<Employee>>() {});
            if(HttpStatus.OK ==  entity.getStatusCode() && entity.getBody() != null){
                SingleResponse<Employee> singleResponse = entity.getBody();
                if(Status.SUCCESS == singleResponse.getStatus()){
                    LOGGER.info("Successfully fetched the employee details with id: {}, message: {}", employeeId,
                            singleResponse.getStatusResponse() != null ? singleResponse.getStatusResponse().getStatusMessage() : "");
                    LOGGER.info(singleResponse.getResponse().toString());
                } else {
                    LOGGER.info("Got failure response from employee management service while fetching the employee details with id: {} ", employeeId);
                    if(singleResponse.getErrorResponse() != null){
                        LOGGER.info("Failed with code: {}, message: {} ",
                                singleResponse.getErrorResponse().getErrorCode(),
                                singleResponse.getErrorResponse().getErrorMessage());
                    }
                }
            }
        } catch (RestClientException rce){
            LOGGER.error("RestClientException occurred while fetching employee with id: {}, exception: {}", employeeId, rce.getMessage());
        } catch (Exception e){
            LOGGER.error("Exception occurred while fetching employee with id: {}, exception: {}", employeeId, e.getMessage());
        }
        LOGGER.info("Performing fetching the employee with id operation - Ended");
    }

    public void getByUsername() {
        LOGGER.info("Performing fetching the employee with username operation - Started");
        String username = "hithikshab";
        String path = endpoints.getGetByUsernameURL();
        String resourcePath = UriComponentsBuilder.fromUriString(path)
                .queryParam("username", username)
                .toUriString();
        try {
            ResponseEntity<SingleResponse<Employee>> entity = client.callEmployeeManagementService(resourcePath, HttpMethod.GET, new ParameterizedTypeReference<SingleResponse<Employee>>() {});
            if(HttpStatus.OK ==  entity.getStatusCode() && entity.getBody() != null){
                SingleResponse<Employee> singleResponse = entity.getBody();
                if(Status.SUCCESS == singleResponse.getStatus()){
                    LOGGER.info("Successfully fetched the employee details with username: {}, message: {}", username,
                            singleResponse.getStatusResponse() != null ? singleResponse.getStatusResponse().getStatusMessage() : "");
                    LOGGER.info(singleResponse.getResponse().toString());
                } else {
                    LOGGER.info("Got failure response from employee management service while fetching the employee details with username: {} ", username);
                    if(singleResponse.getErrorResponse() != null){
                        LOGGER.info("Failed with code: {}, message: {} ",
                                singleResponse.getErrorResponse().getErrorCode(),
                                singleResponse.getErrorResponse().getErrorMessage());
                    }
                }
            }
        } catch (RestClientException rce){
            LOGGER.error("RestClientException occurred while fetching employee with username: {}, exception: {}", username, rce.getMessage());
        } catch (Exception e){
            LOGGER.error("Exception occurred while fetching employee with username: {}, exception: {}", username, e.getMessage());
        }
        LOGGER.info("Performing fetching the employee with username operation - Ended");
    }

    public void deleteById() {
        LOGGER.info("Performing deleting the employee with id operation - Started");
        Integer employeeId = 5;
        String path = endpoints.getDeleteByIdURL();
        String resourcePath = UriComponentsBuilder.fromUriString(path).buildAndExpand(employeeId).toString();
        try {
            ResponseEntity<CustomResponse> entity = client.callEmployeeManagementService(resourcePath, HttpMethod.DELETE, CustomResponse.class);
            if(HttpStatus.OK ==  entity.getStatusCode() && entity.getBody() != null){
                CustomResponse customResponse = entity.getBody();
                if(Status.SUCCESS == customResponse.getStatus()){
                    LOGGER.info("Successfully deleted the employee details with id: {}, message: {}", employeeId,
                            customResponse.getStatusResponse() != null ? customResponse.getStatusResponse().getStatusMessage() : "");
                } else {
                    LOGGER.info("Got failure response from employee management service while deleting the employee details with id: {} ", employeeId);
                    if(customResponse.getErrorResponse() != null){
                        LOGGER.info("Failed with code: {}, message: {} ",
                                customResponse.getErrorResponse().getErrorCode(),
                                customResponse.getErrorResponse().getErrorMessage());
                    }
                }
            }
        } catch (RestClientException rce){
            LOGGER.error("RestClientException occurred while deleting employee with id: {}, exception: {}", employeeId, rce.getMessage());
        } catch (Exception e){
            LOGGER.error("Exception occurred while deleting employee with id: {}, exception: {}", employeeId, e.getMessage());
        }
        LOGGER.info("Performing deleting the employee with id operation - Ended");
    }

    public void deleteByUsername() {
        LOGGER.info("Performing deleting the employee with username operation - Started");
        String username = "anilb";
        String path = endpoints.getDeleteByUsernameURL();
        String resourcePath = UriComponentsBuilder.fromUriString(path)
                .queryParam("username", username)
                .toUriString();
        try {
            ResponseEntity<CustomResponse> entity = client.callEmployeeManagementService(resourcePath, HttpMethod.DELETE, CustomResponse.class);
            if(HttpStatus.OK ==  entity.getStatusCode() && entity.getBody() != null){
                CustomResponse customResponse = entity.getBody();
                if(Status.SUCCESS == customResponse.getStatus()){
                    LOGGER.info("Successfully deleted the employee details with username: {}, message: {}", username,
                            customResponse.getStatusResponse() != null ? customResponse.getStatusResponse().getStatusMessage() : "");
                } else {
                    LOGGER.info("Got failure response from employee management service while deleting the employee details with username: {} ", username);
                    if(customResponse.getErrorResponse() != null){
                        LOGGER.info("Failed with code: {}, message: {} ",
                                customResponse.getErrorResponse().getErrorCode(),
                                customResponse.getErrorResponse().getErrorMessage());
                    }
                }
            }
        } catch (RestClientException rce){
            LOGGER.error("RestClientException occurred while deleting employee with username: {}, exception: {}", username, rce.getMessage());
        } catch (Exception e){
            LOGGER.error("Exception occurred while deleting employee with username: {}, exception: {}", username, e.getMessage());
        }
        LOGGER.info("Performing deleting the employee with username operation - Ended");
    }

    public void deleteAll() {
        LOGGER.info("Performing the delete all operation - Started");
        try {
            ResponseEntity<CustomResponse> entity = client.callEmployeeManagementService(endpoints.getDeleteAllURL(), HttpMethod.DELETE, CustomResponse.class);
            if(HttpStatus.OK ==  entity.getStatusCode() && entity.getBody() != null){
                CustomResponse customResponse = entity.getBody();
                if(Status.SUCCESS == customResponse.getStatus()){
                    LOGGER.info("Successfully deleted all the employees with, message: {}",
                            customResponse.getStatusResponse() != null ? customResponse.getStatusResponse().getStatusMessage() : "");
                } else {
                    LOGGER.info("Got failure response from employee management service while deleting all the employees");
                    if(customResponse.getErrorResponse() != null){
                        LOGGER.info("Failed with code: {}, message: {} ",
                                customResponse.getErrorResponse().getErrorCode(),
                                customResponse.getErrorResponse().getErrorMessage());
                    }
                }
            }
        } catch (RestClientException rce){
            LOGGER.error("RestClientException occurred while deleting all employees, exception: {}", rce.getMessage());
        } catch (Exception e){
            LOGGER.error("Exception occurred while deleting all employees, exception: {}", e.getMessage());
        }
        LOGGER.info("Performing the delete all operation - Ended");
    }
}
