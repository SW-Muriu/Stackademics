package com.indigointelligence.stackademics.DataGeneration;


import com.indigointelligence.stackademics.ProcessCounter.ProcessCounter;
import com.indigointelligence.stackademics.ProcessCounter.ProcessCounterService;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@Slf4j
public class DataGenerationService {

    private static final Logger logger = LoggerFactory.getLogger(DataGenerationService.class);
    private static final int MAX_RECORDS = 1_000_000;
    @Value("${file.storage.path}")
    private String basePath;

    @Autowired
    private ProcessCounterService processCounter;


    private final StudentDataGenerator studentDataGenerator;

    public DataGenerationService(StudentDataGenerator studentDataGenerator) {
        this.studentDataGenerator = studentDataGenerator;
    }

    public String generateStudentData(int numberOfRecords) throws Exception {
        //Input validation
        if (numberOfRecords < 0 || numberOfRecords > MAX_RECORDS) {
            throw new IllegalArgumentException("Number of records must be between 0 and 1,000,000!!");
        }

        //Build fileName and path
        String timeStamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        String fileName = "students_" + timeStamp + ".xlsx";
        String directoryPath = basePath + "dataprocessing/";
        String filePath = directoryPath + fileName;

        File directory = new File(directoryPath);
        if (!directory.exists()) {
            if(!directory.mkdirs()) {
                throw new RuntimeException("Failed to create directory: " + directoryPath);
            }
        }

        //Build the Excel file
        try (SXSSFWorkbook workbook = new SXSSFWorkbook(100)) {
            //TODO: Generate Student Data and save to workbook
            SXSSFSheet sheet = workbook.createSheet("Students");

            Row header = sheet.createRow(0);
            String[] headers = {
                    "StudentId",
                    "FirstName",
                    "LastName",
                    "DOB",
                    "Class",
                    "Score",
                    "Status",
                    "PhotoPath"
            };

            for(int i = 0; i < headers.length; i++) {
                header.createCell(i).setCellValue(headers[i]);
            }

            for(int i =0; i < numberOfRecords; i++) {
                StudentRecord student = studentDataGenerator.generateStudent(i+1);
                Row row = sheet.createRow(i + 1);
                row.createCell(0).setCellValue(student.studentId());
                row.createCell(1).setCellValue(student.firstName());
                row.createCell(2).setCellValue(student.lastName());
                row.createCell(3).setCellValue(student.dob().toString());
                row.createCell(4).setCellValue(student.className());
                row.createCell(5).setCellValue(student.score());
                row.createCell(6).setCellValue(student.status());
                row.createCell(7).setCellValue(student.photoPath());
            }



            //Save file
            try(FileOutputStream fos = new FileOutputStream(filePath)) {
                workbook.write((fos));
            }

            processCounter.trackSuccess("generateStudentData");

        }

        logger.info("Generated {} student records to {}", numberOfRecords, filePath);
        return filePath;

    }
}
