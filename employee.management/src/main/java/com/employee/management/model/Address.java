package com.employee.management.model;

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
public class Address implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer addressId;
    private String houseNumber;
    private String street;
    private String city;
    private String state;
    private String pinCode;

}
