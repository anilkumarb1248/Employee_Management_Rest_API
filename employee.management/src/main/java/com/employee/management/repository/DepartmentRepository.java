package com.employee.management.repository;

import com.employee.management.entity.DepartmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DepartmentRepository extends JpaRepository<DepartmentEntity, Long> {

    Optional<DepartmentEntity> findByDepartmentName(String departmentName);

    void deleteByDepartmentName(String departmentName);
}
