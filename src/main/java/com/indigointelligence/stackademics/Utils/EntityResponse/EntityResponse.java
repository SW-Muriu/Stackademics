package com.indigointelligence.stackademics.Utils.EntityResponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class EntityResponse<T> {
    private String message;
    private Integer statusCode;
    private T entity;
    private Pagination pagination;
}

