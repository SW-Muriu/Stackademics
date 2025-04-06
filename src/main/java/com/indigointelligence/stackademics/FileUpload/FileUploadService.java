package com.indigointelligence.stackademics.FileUpload;

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
    private static final int BATCH_SIZE = 1000;

    @Autowired
    private StudentRepository studentRepository;

    public int processAndSaveExcelFile(MultipartFile file) throws Exception {
        List<Student> students = new ArrayList<>();

        try(XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream())) {
            XSSFSheet sheet = workbook.getSheetAt(0);

            for (int i =0; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null ) continue;

                Student student = new Student();
                student.setStudentId((long) row.getCell(0).getNumericCellValue());
                student.setFirstName(row.getCell(1).getStringCellValue());
                student.setLastName(row.getCell(2).getStringCellValue());
                student.setDob(LocalDate.parse(row.getCell(3).getStringCellValue()));
                student.setClassName(row.getCell(4).getStringCellValue());
                student.setScore((int) row.getCell(5).getNumericCellValue() + 5);
                student.setStatus((int) row.getCell(6).getNumericCellValue());
                student.setPhotoPath(row.getCell(7).getStringCellValue());


                students.add(student);

                //SAve batch
                if (students.size() >= BATCH_SIZE) {
                    studentRepository.saveAll(students);
                    students.clear();
                }
            }

            if (!students.isEmpty()){
                studentRepository.saveAll(students);
            }
            int totalRecords = sheet.getLastRowNum();
            logger.info("Processed and saved {} student records from file: {}", totalRecords, file.getOriginalFilename());
            return totalRecords;
        }
    }
}
