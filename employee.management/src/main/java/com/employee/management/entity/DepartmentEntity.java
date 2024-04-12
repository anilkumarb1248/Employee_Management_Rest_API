package com.employee.management.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "DEPARTMENT")
public class DepartmentEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "DEPARTMENT_ID")
//	@GeneratedValue(strategy = GenerationType.AUTO) // Inserting the ID's directly
    private Long departmentId;

    @Column(name = "DEPARTMENT_NAME")
    private String departmentName;
}
