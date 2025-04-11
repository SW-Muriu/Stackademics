package com.indigointelligence.stackademics.DataGeneration;

import com.indigointelligence.stackademics.DataGeneration.Records.DataGenerationRequest;
import com.indigointelligence.stackademics.Utils.EntityResponse.EntityResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/data/")
public class DataGenerationController {

    @Autowired DataGenerationService dataGenerationService;

    @PostMapping("generate")
    public EntityResponse<?> generateData(@RequestBody DataGenerationRequest request) {
        try {
            String filePath = dataGenerationService.generateStudentData(request.numberOfRecords());

            return EntityResponse.builder()
                    .statusCode(HttpStatus.OK.value())
                    .message("Data generated successfully")
                    .entity(filePath)
                    .build();

        } catch (IllegalArgumentException e) {
            return EntityResponse.builder()
                    .statusCode(HttpStatus.BAD_REQUEST.value())
                    .message(e.getMessage())
                    .build();

        } catch (Exception e) {
            return EntityResponse.builder()
                    .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .message("Error generating data: " + e.getMessage())
                    .build();
        }
    }
}
