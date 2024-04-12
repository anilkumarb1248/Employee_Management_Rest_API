package com.employee.management.response;

import com.employee.management.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CustomResponse {

    private Status status;

    private StatusResponse statusResponse;

    private ErrorResponse errorResponse;


    public CustomResponse(Status status, StatusResponse statusResponse) {
        this.status = status;
        this.statusResponse = statusResponse;
    }

    public CustomResponse(Status status, ErrorResponse errorResponse) {
        this.status = status;
        this.errorResponse = errorResponse;
    }
}
