package com.employee.client.reader;

import com.employee.client.model.Employee;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeeJsonDataReader {

    private final Logger LOGGER = LoggerFactory.getLogger(EmployeeJsonDataReader.class);

    private static final String JSON_DATA_FILES_PATH = "data";

    private ObjectMapper objectMapper = new ObjectMapper();

    private Employee singleEmployeeData;
    private List<Employee> employeeListData;
    private Employee updateEmployeeData;

    private boolean dataLoaded = false;

    public EmployeeJsonDataReader(){
        objectMapper.registerModule(new JavaTimeModule());
        readData();
    }

    public void readData(){
        if(!isDataLoaded()){
            List<Employee> singleEmp = readeFromJsonFile("single-employee.json");
            if(CollectionUtils.isNotEmpty(singleEmp)){
                singleEmployeeData = singleEmp.get(0);
            }
            List<Employee> listEmp = readeFromJsonFile("list-employees.json");
            if(CollectionUtils.isNotEmpty(singleEmp)){
                employeeListData = listEmp;
            }
            List<Employee> updateEmp = readeFromJsonFile("update-employee.json");
            if(CollectionUtils.isNotEmpty(singleEmp)){
                updateEmployeeData = updateEmp.get(0);
            }
            if(singleEmployeeData != null && employeeListData != null && updateEmployeeData != null){
                dataLoaded = true;
            }
        }
    }

    private List<Employee> readeFromJsonFile(String fileName){
        List<Employee> list = new ArrayList<>();
        try {
            LOGGER.info("Reading the employees data from file: {}", fileName);
            Resource resource = new ClassPathResource(JSON_DATA_FILES_PATH + "/" + fileName);
            InputStream inputStream = resource.getInputStream();
            TypeReference<List<Employee>> typeReference = new TypeReference<>() {};
            list = objectMapper.readValue(inputStream, typeReference);
            LOGGER.info("Read the data from file: {} successfully, size: {}", fileName, list.size());
        } catch (Exception e) {
            LOGGER.error("Failed to read the data from file: {}, exception: {}", fileName, e.getMessage());
        }
        return list;
    }

    public boolean isDataLoaded() {
        return dataLoaded;
    }

    public Employee getSingleEmployeeData() {
        return singleEmployeeData;
    }

    public List<Employee> getEmployeeListData() {
        return employeeListData;
    }

    public Employee getUpdateEmployeeData() {
        return updateEmployeeData;
    }
}
