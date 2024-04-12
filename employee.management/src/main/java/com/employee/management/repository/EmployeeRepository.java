package com.employee.management.repository;

import com.employee.management.entity.EmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<EmployeeEntity, Integer> {

    Optional<EmployeeEntity> findByUserName(String userName);

    List<EmployeeEntity> findByDepartmentId(Long departmentId);

    List<EmployeeEntity> findBySalaryBetween(Double start, Double end);

    List<EmployeeEntity> findByMobileNumberStartingWith(String start);

    List<EmployeeEntity> findByDobBefore(LocalDate date);

    List<EmployeeEntity> findBySalaryGreaterThanEqual(Double salary);

    List<EmployeeEntity> findBySalaryLessThan(Double salary);

    @Query(nativeQuery = true, value="select DEPARTMENT_ID, count(*) from employee where DEPARTMENT_ID>=10 group by DEPARTMENT_ID")
    List<Object[]> findEmployeeCountByDept();
}
