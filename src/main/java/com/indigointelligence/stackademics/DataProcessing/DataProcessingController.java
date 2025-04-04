package com.indigointelligence.stackademics.DataProcessing;


import com.indigointelligence.stackademics.Utils.EntityResponse.EntityResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.swing.text.html.parser.Entity;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/data/")
public class DataProcessingController {

    @Autowired
    private DataProcessingService dataProcessingService;

    @PostMapping("process")
    public EntityResponse<?> processData() {
        try {
            String csvFilePath = dataProcessingService.processLatestExcelFile();
            return EntityResponse.builder()
                    .statusCode(HttpStatus.OK.value())
                    .message("Data processed successfully")
                    .entity(csvFilePath)
                    .build();
        } catch (IllegalStateException e) {
            return EntityResponse.builder()
                    .statusCode(HttpStatus.NOT_FOUND.value())
                    .message(e.getMessage())
                    .build();
        } catch (Exception e) {
            return EntityResponse.builder()
                    .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .message("Error processing data: " + e.getMessage())
                    .build();
        }
    }

}
