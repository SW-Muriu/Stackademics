package com.indigointelligence.stackademics.FileUpload;


import com.indigointelligence.stackademics.Utils.EntityResponse.EntityResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@CrossOrigin
@RequestMapping("api/v1/data")
public class FileUploadController {


    @Autowired
    private FileUploadService fileUploadService;


    @PostMapping("/file")
    public EntityResponse<?> uploadFile(@RequestParam("file") MultipartFile file) {
        try {

            if (file.isEmpty() || !file.getOriginalFilename().endsWith(".xlsx")) {
                return EntityResponse.builder()
                        .statusCode(HttpStatus.BAD_REQUEST.value())
                        .message("Please upload a valid .xlsx file")
                        .build();
            }

            int recordsSaved = fileUploadService.processAndSaveExcelFile(file);;
            return EntityResponse.builder()
                    .statusCode(HttpStatus.OK.value())
                    .message("File uploaded and processed successfully")
                    .entity("Saved " + recordsSaved + " records")
                    .build();
        } catch (IllegalArgumentException e) {
            return EntityResponse.builder()
                    .statusCode(HttpStatus.BAD_REQUEST.value())
                    .message(e.getMessage())
                    .build();
        } catch (Exception e) {
            return EntityResponse.builder()
                    .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .message("Error processing file: " + e.getMessage())
                    .build();
        }

    }
}
