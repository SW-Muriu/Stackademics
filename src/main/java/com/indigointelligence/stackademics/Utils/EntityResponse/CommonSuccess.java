package com.indigointelligence.stackademics.Utils.EntityResponse;

import org.springframework.http.HttpStatus;

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
