package com.employee.management.model;

import com.employee.management.enums.Gender;
import com.employee.management.enums.MaritalStatus;
import com.employee.management.enums.Role;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class Employee implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private Integer empId;
    private String userName;
    private String firstName;
    private String middleName;
    private String lastName;
    private String fatherName;
    private String motherName;
    private String guardianName;
    private Role role;
    private Double salary;
    private LocalDate dob;
    private Gender gender;
    private String mobileNumber;
    private String homeTelephone;
    private String email;
    private String personalEmail;
    private MaritalStatus maritalStatus;
    private String spouseName;
    private Long departmentId;
    private List<Address> addresses;

}
