package com.employee.management.entity;

import com.employee.management.enums.Gender;
import com.employee.management.enums.MaritalStatus;
import com.employee.management.enums.Role;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "EMPLOYEE")
public class EmployeeEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "EMPLOYEE_ID")
    //@GeneratedValue(strategy = GenerationType.AUTO)
    @SequenceGenerator(initialValue = 1, name = "emp_seq", sequenceName = "EMPLOYEE_SEQUENCE")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "emp_seq")
    private Integer empId;

    @Column(name = "USER_NAME")
    private String userName;

    @Column(name = "FIRST_NAME")
    private String firstName;

    @Column(name = "MIDDL_ENAME")
    private String middleName;

    @Column(name = "LAST_NAME")
    private String lastName;

    @Column(name = "FATHER_NAME")
    private String fatherName;

    @Column(name = "MOTHER_NAME")
    private String motherName;

    @Column(name = "GUARDIAN_NAME")
    private String guardianName;

    @Column(name = "ROLE")
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name = "SALARY")
    private Double salary;

    @Column(name = "DATE_OF_BIRTH")
    private LocalDate dob;

    @Column(name = "MOBILE_NUMBER")
    private String mobileNumber;

    @Column(name = "HOME_TELEPHONE")
    private String homeTelephone;
    @Column(name = "EMAIL")
    private String email;
    @Column(name = "PERSONAL_EMAIL")
    private String personalEmail;

    @Column(name = "GENDER")
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(name = "MARITAL_STATUS")
    @Enumerated(EnumType.STRING)
    private MaritalStatus maritalStatus;

    @Column(name = "SPOUSE_NAME")
    private String spouseName;

    @Column(name = "DEPARTMENT_ID")
    private Long departmentId;

    @OneToMany(targetEntity = AddressEntity.class, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "EMPLOYEE_REF_ID", referencedColumnName = "EMPLOYEE_ID")
//	@ElementCollection // For testing enable this and comment above lines
    private List<AddressEntity> addresses;
}
