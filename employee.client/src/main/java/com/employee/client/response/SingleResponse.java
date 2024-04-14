package com.employee.client.response;

public class SingleResponse<T> extends CustomResponse {

    private T response;

    public T getResponse() {
        return response;
    }

    public void setResponse(T response) {
        this.response = response;
    }
}
