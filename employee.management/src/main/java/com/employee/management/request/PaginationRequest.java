package com.employee.management.request;

import com.employee.management.enums.SortOrder;
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
public class PaginationRequest {

    private Integer pageNumber;
    private Integer  pageSize;
    private SortOrder sortOrder;
    private String sortingBy;

}
