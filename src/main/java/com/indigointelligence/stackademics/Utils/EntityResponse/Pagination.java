package com.indigointelligence.stackademics.Utils.EntityResponse;

public interface Pagination {
        int pageSize();
        int pageIndex();
        int totalRecords();

        int DEFAULT_PAGE_SIZE = 10;
        int DEFAULT_PAGE_INDEX = 0;
}
