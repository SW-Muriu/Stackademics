package com.indigointelligence.stackademics.Utils.PageMetaData;

import com.indigointelligence.stackademics.Utils.EntityResponse.Pagination;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class PageMetaData implements Pagination {
    private int pageSize;
    private int pageIndex;
    private int totalRecords;

    @Override
    public int pageSize() {
        return 0;
    }

    @Override
    public int pageIndex() {
        return 0;
    }

    @Override
    public int totalRecords() {
        return 0;
    }
}
