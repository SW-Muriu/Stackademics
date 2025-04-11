package com.indigointelligence.stackademics.DataProcessing;

import com.indigointelligence.stackademics.ProcessCounter.ProcessCounterService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.poi.sl.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@Service
public class DataProcessingService {
    private static final Logger logger = LoggerFactory.getLogger(DataProcessingService.class);
    private static final String[] HEADERS = {
            "StudentId",
            "FirstName",
            "LastName",
            "DOB",
            "Class",
            "Score",
            "Status",
            "PhotoPath"
    };

    @Value("${file.storage.path}")
    private String basePath;

    @Autowired
    private ProcessCounterService processCounterService;

    public String processLatestExcelFile() throws Exception {
        File directory = new File(basePath + "dataprocessing/");

        System.out.println("Selected file path: " + directory);

        File[] excelFiles = directory.listFiles((dir, name) -> name.endsWith(".xlsx"));

        if (excelFiles == null || excelFiles.length ==0) {
            throw new IllegalStateException("No excel files found in " + directory.getPath());
        }

        File latestFile = null;
        long latestTime = Long.MIN_VALUE;
        for (File file: excelFiles) {
            if (file.lastModified() > latestTime) {
                latestTime = file.lastModified();
                latestFile = file;
            }
        }

        if (latestFile == null) {
            throw new IllegalStateException("No valid excel file found");
        }

        //Generate CSV path
        String timeStamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        String csvFileNAME = "processed_students_" + timeStamp + ".csv";
        String csvFilePath = basePath + "dataprocessing/" + csvFileNAME;


        //Build csv file
        try (FileInputStream fis = new FileInputStream(latestFile);
             XSSFWorkbook workbook = new XSSFWorkbook(fis);
             FileWriter fileWriter = new FileWriter(csvFilePath);
             CSVPrinter csvPrinter = new CSVPrinter(fileWriter, CSVFormat.DEFAULT.withHeader(HEADERS))) {
            XSSFSheet sheet = workbook.getSheetAt(0);
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null) continue;

                long studentId = (long) row.getCell(0).getNumericCellValue();
                String firstName = row.getCell(1).getStringCellValue();
                String lastName = row.getCell(2).getStringCellValue();
                LocalDate dob = LocalDate.parse(row.getCell(3).getStringCellValue());
                String className = row.getCell(4).getStringCellValue();
                int score = (int) row.getCell(5).getNumericCellValue() + 10;
                int status = (int) row.getCell(6).getNumericCellValue();
                String photoPath = row.getCell(7).getStringCellValue();

                csvPrinter.printRecord(studentId, firstName, lastName, dob, className, score, status, photoPath);
            }
        }
        logger.info("Processed Excel File {} to CSV: {}", latestFile.getPath(), csvFilePath);
        processCounterService.trackSuccess("processLatestExcelFile");
        return csvFilePath;
    }
}
