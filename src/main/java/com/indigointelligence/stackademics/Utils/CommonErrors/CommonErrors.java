package com.indigointelligence.stackademics.Utils.CommonErrors;

import com.indigointelligence.stackademics.Utils.EntityResponse.EntityResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class CommonErrors {

    public EntityResponse<?> error500() {
        return EntityResponse.builder()
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message("Internal Server Error!!")
                .build();
    }

    public EntityResponse<?> error404() {
        return EntityResponse.builder()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .message("Resource(s) Not Found!!")
                .build();
    }

    public EntityResponse<?> error404(String message) {
        return EntityResponse.builder()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .message(message)
                .build();
    }

    public EntityResponse<?> error400(String message){
        if (message.isEmpty()) {
           return EntityResponse.builder()
                   .statusCode(HttpStatus.BAD_REQUEST.value())
                   .build();
        } else {
            return EntityResponse.builder()
                    .message(message)
                    .statusCode(HttpStatus.BAD_REQUEST.value())
                    .build();
        }
    }
}
