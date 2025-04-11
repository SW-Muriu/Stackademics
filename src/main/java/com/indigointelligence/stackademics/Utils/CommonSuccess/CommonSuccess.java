package com.indigointelligence.stackademics.Utils.CommonSuccess;

import com.indigointelligence.stackademics.Utils.EntityResponse.EntityResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class CommonSuccess {

    public EntityResponse<?> empty200() {
        return EntityResponse.builder()
                .message("Request(s) completed successfully")
                .statusCode(HttpStatus.OK.value())
                .build();
    }

    public EntityResponse<?> empty200(String message) {
        return EntityResponse.builder()
                .message(message)
                .statusCode(HttpStatus.OK.value())
                .build();
    }
}
