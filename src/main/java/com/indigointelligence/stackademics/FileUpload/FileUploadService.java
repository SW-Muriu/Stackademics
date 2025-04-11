package com.indigointelligence.stackademics.FileUpload;

import com.indigointelligence.stackademics.ProcessCounter.ProcessCounterService;
import com.indigointelligence.stackademics.Student.Student;
import com.indigointelligence.stackademics.Student.StudentRepository;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class FileUploadService {

    private static final Logger logger = LoggerFactory.getLogger(FileUploadService.class);
    private static final int BATCH_SIZE = 1;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private ProcessCounterService counterService;

    public int processAndSaveExcelFile(MultipartFile file) throws Exception {
        List<Student> students = new ArrayList<>();

        try(XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream())) {
            XSSFSheet sheet = workbook.getSheetAt(0);

            for (int i = 1; i <= ((sheet.getLastRowNum())-1); i++) {
                Row row = sheet.getRow(i);
                System.out.println("Row: " + row.getCell(6));

                Student student = new Student();
//                student.setStudentId((long) row.getCell(0).getNumericCellValue());
                student.setFirstName(row.getCell(1).getStringCellValue());
                student.setLastName(row.getCell(2).getStringCellValue());
                student.setDob(LocalDate.parse(row.getCell(3).getStringCellValue()));
                student.setClassName(row.getCell(4).getStringCellValue());
                student.setScore((int) row.getCell(5).getNumericCellValue() + 5);
                student.setStatus((int) row.getCell(6).getNumericCellValue());
                if ((row.getCell(7) == null)) {
                    student.setPhotoPath("");
                } else {
                    student.setPhotoPath(row.getCell(7).getStringCellValue());
                }


                students.add(student);

                //SAve batch
                studentRepository.saveAll(students);
                students.clear();
            }
            int totalRecords = sheet.getLastRowNum();
            logger.info("Processed and saved {} student records from file: {}", totalRecords, file.getOriginalFilename());
            counterService.trackSuccess("processAndSaveExcelFile");
            return totalRecords;
        }
    }
}
