package com.employee.client.request;

import com.employee.client.enums.SortOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class PaginationRequest {

    private Integer pageNumber;
    private Integer  pageSize;
    private SortOrder sortOrder;
    private String sortingBy;

}
