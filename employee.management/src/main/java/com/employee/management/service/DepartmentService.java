package com.employee.management.service;

import com.employee.management.entity.DepartmentEntity;
import com.employee.management.exceptions.ApplicationInternalException;
import com.employee.management.exceptions.DepartmentNotFoundException;
import com.employee.management.exceptions.DuplicateDepartmentException;
import com.employee.management.model.Department;
import com.employee.management.repository.DepartmentRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DepartmentService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DepartmentService.class);
    private final DepartmentRepository departmentRepository;


    @Autowired // @Autowired is not required as we are using single constructor
	public DepartmentService(DepartmentRepository departmentRepository) {
		this.departmentRepository = departmentRepository;
	}

    
    public List<Department> getDepartments() {
        List<DepartmentEntity> entityList = departmentRepository.findAll();
        List<Department> departmentList = new ArrayList<>();
        entityList.stream().forEach(entity -> {
            departmentList.add(convertToBean(entity));
        });
        return departmentList;
    }

    
    public Department getDepartment(Long departmentId) {
        Optional<DepartmentEntity> optional = departmentRepository.findById(departmentId);
        if (optional.isEmpty()) {
            throw new DepartmentNotFoundException("Department not found with id: " + departmentId);
        }
        return convertToBean(optional.get());
    }

    
    public Department getDepartment(String departmentName) {
        Optional<DepartmentEntity> optional = departmentRepository.findByDepartmentName(departmentName);
        if (optional.isEmpty()) {
            throw new DepartmentNotFoundException("Department not found with name: " + departmentName);
        }
        return convertToBean(optional.get());
    }

    
    public boolean addDepartment(Department Department) {
        if (!isDuplicateDepartment(true, Department)) {
            DepartmentEntity entity = departmentRepository.save(convertToEntity(Department));

            if (null != entity) {
                return true;
            } else {
                return false;
            }
        } else {
            throw new DuplicateDepartmentException(
                    "Department already exist with name: " + Department.getDepartmentName());
        }
    }

    
    public boolean addDepartmentsList(List<Department> departmentList) {
        List<DepartmentEntity> entityList = new ArrayList<>();
        departmentList.forEach(dto -> {
            if (!isDuplicateDepartment(true, dto)) {
                entityList.add(convertToEntity(dto));
            }
        });
        try{
            departmentRepository.saveAll(entityList);
            LOGGER.info("Inserted departments dummy data successfully");
        } catch (Exception e){
            throw new ApplicationInternalException(e.getMessage());
        }
        return true;
    }

    
    public boolean updateDepartment(Department Department) {
        if (isDepartmentExist(Department.getDepartmentId())) {
            if (isDuplicateDepartment(false, Department)) {
                throw new DuplicateDepartmentException(
                        "Department already exist with name: " + Department.getDepartmentName());
            }

            try {
                departmentRepository.save(convertToEntity(Department));
                LOGGER.info("Updated department data successfully");
                return true;
            } catch (Exception e) {
                throw new ApplicationInternalException(e.getMessage());
            }
        } else {
            throw new DepartmentNotFoundException("No Department found with id: " + Department.getDepartmentId());
        }
    }

    
    public boolean deleteDepartment(Long departmentId) {
        if (isDepartmentExist(departmentId)) {
            departmentRepository.deleteById(departmentId);
            return true;
        } else {
            throw new DepartmentNotFoundException("No Department found with id: " + departmentId);
        }
    }

    
    @Transactional
    public boolean deleteDepartmentByName(String departmentName) {
        if (isDepartmentExistByName(departmentName)) {
            try {
                departmentRepository.deleteByDepartmentName(departmentName);
                return true;
            } catch (Exception ex) {
                LOGGER.error("Failed to delete department data with name: {}, exception: {} ", departmentName, ex.getMessage());
                throw new ApplicationInternalException(ex.getMessage());
            }
        } else {
            throw new DepartmentNotFoundException("No Department found with name: " + departmentName);
        }
    }

    
    public boolean deleteAll() {
        try{
            departmentRepository.deleteAll();
            return true;
        } catch (Exception e){
            throw new ApplicationInternalException(e.getMessage());
        }
    }

    
    public boolean addDummyData() {
        LOGGER.info("Dummy Departments insertion started");
        try {
            ObjectMapper mapper = new ObjectMapper();
            Resource resource = new ClassPathResource("departments-data.json");
            InputStream inputStream = resource.getInputStream();
            TypeReference<List<Department>> typeReference = new TypeReference<List<Department>>() {
            };

            List<Department> dtoList = mapper.readValue(inputStream, typeReference);

            List<DepartmentEntity> entityList = new ArrayList<>();
            dtoList.forEach(dto -> {
                if (!isDuplicateDepartment(true, dto)) {
                    entityList.add(convertToEntity(dto));
                }
            });
            departmentRepository.saveAll(entityList);

            LOGGER.info("Inserted departments dummy data successfully");
        } catch (Exception e) {
            LOGGER.error("Failed to insert departments dummy data, exception: {}", e.getMessage());
        }
        LOGGER.info("Dummy Departments insertion ended");
        return true;
    }

    private DepartmentEntity convertToEntity(Department department) {
        DepartmentEntity entity = new DepartmentEntity();
        BeanUtils.copyProperties(department, entity);
        return entity;
    }

    private Department convertToBean(DepartmentEntity entity) {
        Department department = new Department();
        BeanUtils.copyProperties(entity, department);
        return department;
    }

    private boolean isDuplicateDepartment(boolean newRecordFlag, Department Department) {

        Optional<DepartmentEntity> optional = departmentRepository
                .findByDepartmentName(Department.getDepartmentName());
        if (optional.isEmpty()) {
            return false;
        } else {
            DepartmentEntity departmentEntity = optional.get();
            if (newRecordFlag || departmentEntity.getDepartmentId() != Department.getDepartmentId()) {
                return true;
            }
        }
        return false;
    }

    private boolean isDepartmentExist(Long id) {
        return departmentRepository.existsById(id);
    }

    private boolean isDepartmentExistByName(String departmentName) {
        Optional<DepartmentEntity> optional = departmentRepository.findByDepartmentName(departmentName);
        return optional.isPresent();
    }
    
}
