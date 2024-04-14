package com.employee.client.response;

import java.util.List;

public class ListResponse<T> extends CustomResponse {
    private List<T> responseList;

    public List<T> getResponseList() {
        return responseList;
    }

    public void setResponseList(List<T> responseList) {
        this.responseList = responseList;
    }
}
