package com.employee.management.response;

import com.employee.management.response.CustomResponse;

public class SingleResponse<T> extends CustomResponse {

    private T response;

    public T getResponse() {
        return response;
    }

    public void setResponse(T response) {
        this.response = response;
    }
}
