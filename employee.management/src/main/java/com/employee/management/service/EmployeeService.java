package com.employee.management.service;

import com.employee.management.entity.AddressEntity;
import com.employee.management.entity.EmployeeEntity;
import com.employee.management.enums.SortOrder;
import com.employee.management.exceptions.ApplicationInternalException;
import com.employee.management.exceptions.DuplicateEmployeeException;
import com.employee.management.exceptions.EmployeeNotFoundException;
import com.employee.management.model.Address;
import com.employee.management.model.Employee;
import com.employee.management.repository.EmployeeRepository;
import com.employee.management.request.PaginationRequest;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeService.class);

    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public List<Employee> getEmployeeList() {
        LOGGER.info("Fetching the employees list");
        List<Employee> employeesList = new ArrayList<>();
        try {
            List<EmployeeEntity> entitiesList = employeeRepository.findAll();
            if (CollectionUtils.isNotEmpty(entitiesList)) {
                employeesList = entitiesList.stream().map(this::convertToBean).toList();
            } else {
                LOGGER.info("Empty employee list is returning");
            }
        } catch (Exception e) {
            LOGGER.error("Exception occurred while fetching all the employees list");
        }
        LOGGER.info("Fetched the employee list, size: {}", employeesList.size());
        return employeesList;
    }

    public List<Employee> getEmployeesByPagination(PaginationRequest request) {
        LOGGER.info("Fetching the employees list based on the pagination");
        List<Employee> employeesList;
        try {
            // Page number starts from 0
            Sort sort = Sort.by(Sort.Direction.fromString(request.getSortOrder().toString()), request.getSortingBy());
            Pageable pageable = PageRequest.of(request.getPageNumber(), request.getPageSize(), sort);
            Page<EmployeeEntity> page = employeeRepository.findAll(pageable);
            List<EmployeeEntity> entitiesList = page.getContent();
            employeesList = entitiesList.stream().map(this::convertToBean).toList();
        } catch (Exception e) {
            LOGGER.error("Exception occurred while fetching the employees by pagination, exception: {}", e.getMessage());
            throw new ApplicationInternalException(e.getMessage());
        }
        LOGGER.info("Fetched the employees list based on the pagination, size: {}", employeesList.size());
        return employeesList;
    }

    @Cacheable(cacheNames = "employees", key = "#employeeId")
    public Employee getEmployee(Integer employeeId) {
        Optional<EmployeeEntity> optional = employeeRepository.findById(employeeId);
        if (optional.isEmpty()) {
            LOGGER.error("No employee found with empId: {} ", employeeId);
            throw new EmployeeNotFoundException("No employee found with id:" + employeeId);
        }
        EmployeeEntity employeeEntity = optional.get();
        return convertToBean(employeeEntity);
    }

    public Integer addEmployee(Employee employee) {
        if (!isDuplicateEmployee(employee)) {
            EmployeeEntity emp = employeeRepository.save(convertToEntity(employee));
            return emp.getEmpId();
        } else {
            LOGGER.error("Employee already exist with userName: {}", employee.getUserName());
            throw new DuplicateEmployeeException("Employee already exist with userName: " + employee.getUserName());
        }

    }

    public boolean addEmployees(List<Employee> employees) {
        List<EmployeeEntity> employeeEntities = employees.stream()
                .filter(employee -> !isDuplicateEmployee(employee))
                .map(this::convertToEntity).toList();
        try {
            if (!employeeEntities.isEmpty()) {
                employeeRepository.saveAll(employeeEntities);
            }
        } catch (Exception e) {
            LOGGER.error("Exception occurred while adding the list of employees, exception: {}", e.getMessage());
            throw new ApplicationInternalException(e.getMessage());
        }
        return true;
    }

    public boolean updateEmployee(Employee employee) {
        if (isEmployeeExist(employee.getEmpId())) {
            if (isDuplicateEmployee(employee)) {
                LOGGER.error("Employee already exist with userName: {}", employee.getUserName());
                throw new DuplicateEmployeeException("Employee already exist with userName: " + employee.getUserName());
            }
            try {
                employeeRepository.save(convertToEntity(employee));
                return true;
            } catch (Exception e) {
                LOGGER.error("Exception occurred while updating the employee  with empId: {}, username: {}, exception: {} ", employee.getEmpId(), employee.getUserName(), e.getMessage());
                throw new ApplicationInternalException(e.getMessage());
            }
        } else {
            LOGGER.error("No employee found to update with empId: {}, username: {} ", employee.getEmpId(), employee.getUserName());
            throw new EmployeeNotFoundException("No employee found with id: " + employee.getEmpId());
        }
    }

    public boolean deleteEmployee(Integer employeeId) {
        if (isEmployeeExist(employeeId)) {
            try {
                employeeRepository.deleteById(employeeId);
                return true;
            } catch (Exception e) {
                LOGGER.error("Exception occurred while deleting the employee  with empId: {}, exception: {} ", employeeId, e.getMessage());
                throw new ApplicationInternalException(e.getMessage());
            }
        } else {
            LOGGER.error("No employee found to delete with id: {} ", employeeId);
            throw new EmployeeNotFoundException("No employee found with id: " + employeeId);
        }
    }

    public boolean deleteAll() {
        try {
            //employeeRepository.deleteAllInBatch(); // Not working due to Address constraints
            List<EmployeeEntity> entitiesList = employeeRepository.findAll();
            employeeRepository.deleteAll(entitiesList);
            return true;
        } catch (Exception e) {
            LOGGER.error("Exception occurred while deleting all the employees, exception: {} ", e.getMessage());
            throw new ApplicationInternalException("Failed to delete employees, with error" + e.getMessage());
        }
    }

    @Cacheable(cacheNames = "employees", key = "#name")
    public Employee getEmployeeByName(String userName) {
        Optional<EmployeeEntity> optional = employeeRepository.findByUserName(userName);
        if (optional.isEmpty()) {
            LOGGER.error("No employee found with userName: {} ", userName);
            throw new EmployeeNotFoundException("No employee found with userName:" + userName);
        }
        EmployeeEntity employeeEntity = optional.get();
        return convertToBean(employeeEntity);
    }

    public boolean deleteEmployeeByUserName(String userName) {

        Optional<EmployeeEntity> optional = employeeRepository.findByUserName(userName);
        if (optional.isPresent()) {
            try {
                employeeRepository.deleteById(optional.get().getEmpId());
                return true;
            } catch (Exception e) {
                LOGGER.error("Exception occurred while deleting the employee  with userName: {}, exception: {} ", userName, e.getMessage());
                throw new ApplicationInternalException(e.getMessage());
            }
        } else {
            LOGGER.error("No employee found to delete with userName: {} ", userName);
            throw new EmployeeNotFoundException("No employee found with userName: " + userName);
        }
    }

    public boolean addDummyData() {
        LOGGER.info("Inserting employees dummy data");
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            Resource resource = new ClassPathResource("data/employees-data.json");
            InputStream inputStream = resource.getInputStream();
            TypeReference<List<Employee>> typeReference = new TypeReference<>() {
            };

            List<Employee> list = mapper.readValue(inputStream, typeReference);

            List<EmployeeEntity> entityList = new ArrayList<>();
            list.forEach(employee -> {
                if (!isDuplicateEmployee(employee)) {
                    entityList.add(convertToEntity(employee));
                }
            });
            employeeRepository.saveAll(entityList);
            LOGGER.info("Inserted employees dummy data successfully, size: {}", entityList.size());
        } catch (Exception e) {
            LOGGER.error("Failed to insert employee dummy data, exception: {}", e.getMessage());
            throw new ApplicationInternalException("Failed to insert employee dummy data, with error: " + e.getMessage());
        }
        return true;
    }

    private Employee convertToBean(EmployeeEntity employeeEntity) {
        Employee employee = new Employee();

        BeanUtils.copyProperties(employeeEntity, employee);
        List<AddressEntity> addressEntityList = employeeEntity.getAddresses();

        if (null != addressEntityList && !addressEntityList.isEmpty()) {
            List<Address> addressList = new ArrayList<>();

            addressEntityList.forEach(addressEntity -> {
                Address address = new Address();
                BeanUtils.copyProperties(addressEntity, address);
                addressList.add(address);
            });
            employee.setAddresses(addressList);
        }
        return employee;
    }

    private EmployeeEntity convertToEntity(Employee employee) {
        EmployeeEntity employeeEntity = new EmployeeEntity();

        BeanUtils.copyProperties(employee, employeeEntity);

        List<Address> addressList = employee.getAddresses();

        if (null != addressList && !addressList.isEmpty()) {
            List<AddressEntity> addressEntityList = new ArrayList<>();
            addressList.forEach(address -> {
                AddressEntity addressEntity = new AddressEntity();
                BeanUtils.copyProperties(address, addressEntity);
                addressEntityList.add(addressEntity);
            });
            employeeEntity.setAddresses(addressEntityList);
        }
        return employeeEntity;
    }


    private boolean isDuplicateEmployee(Employee employee) {
        boolean newRecordFlag = employee.getEmpId() == null;
        Optional<EmployeeEntity> optional = employeeRepository.findByUserName(employee.getUserName());
        if (optional.isEmpty()) {
            return false;
        } else {
            // We should not compare the Integer, Long values with == operator, it will only check the reference
            //return newRecordFlag || employee.getEmpId().intValue() != optional.get().getEmpId().intValue();
            return newRecordFlag || !employee.getEmpId().equals(optional.get().getEmpId());
        }
    }

    private boolean isEmployeeExist(Integer id) {
//		Optional<EmployeeEntity> optional = employeeRepository.findById(id);
//		return optional.isPresent();
        return employeeRepository.existsById(id);
    }

}
