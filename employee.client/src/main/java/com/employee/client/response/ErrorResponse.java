package com.employee.client.response;

import lombok.AllArgsConstructor;
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
public class ErrorResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    private String errorCode;
    private String errorMessage;
}
