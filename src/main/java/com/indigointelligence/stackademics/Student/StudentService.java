package com.indigointelligence.stackademics.Student;

import com.indigointelligence.stackademics.ProcessCounter.ProcessCounterService;
import com.indigointelligence.stackademics.Utils.CommonErrors.CommonErrors;
import com.indigointelligence.stackademics.Utils.CommonSuccess.CommonSuccess;
import com.indigointelligence.stackademics.Utils.EntityResponse.EntityResponse;
import com.indigointelligence.stackademics.Utils.EntityResponse.Pagination;
import com.indigointelligence.stackademics.Utils.PageMetaData.PageMetaData;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired CommonErrors commonErrors;

    @Autowired
    CommonSuccess commonSuccess;

    private final static Logger logger = LoggerFactory.getLogger(Student.class);

    @Autowired
    private ProcessCounterService processCounterService;

    public EntityResponse<?> postStudent(Student incomdingStudent) {

        try {
                incomdingStudent.setPostedBy("");
                incomdingStudent.setPostedFlag('Y');
                incomdingStudent.setPostedTime(LocalDateTime.now());

                studentRepository.save(incomdingStudent);

                return commonSuccess.empty200("Student posted successfully");
        } catch (Exception e) {
            logger.error("Error in posting student: ", e);
            return commonErrors.error500();
        }

    }

    public EntityResponse<?> fetchAll(Integer pageSize, Integer pageIndex, String searchTerm) {
        try {
            int effectivePageSize = (pageSize != null && pageSize > 0) ? pageSize : Pagination.DEFAULT_PAGE_SIZE;
            int effectivePageIndex = (pageIndex != null && pageIndex >= 0) ? pageIndex : Pagination.DEFAULT_PAGE_INDEX;

            Pageable pageable = PageRequest.of(effectivePageIndex, effectivePageSize);

            boolean validSearch = searchTerm != null && !searchTerm.trim().isEmpty() &&
                    !"undefined".equalsIgnoreCase(searchTerm.trim()) &&
                    !"searchTerm".equalsIgnoreCase(searchTerm.trim());

            Page<Student> studentPage = validSearch
                    ? studentRepository.searchByStatusAndName(1, searchTerm, pageable)
                    : studentRepository.findByStatus(1, pageable);

            List<Student> existingStudents = studentPage.getContent();

            if (existingStudents.isEmpty()) {
                return commonErrors.error404();
            }

            PageMetaData pagination = new PageMetaData(
                    studentPage.getSize(),
                    studentPage.getNumber(),
                    (int) studentPage.getTotalElements()
            );

            //Process Runs
            int dataProcessRuns = processCounterService.getSuccessCount("processLatestExcelFile");
            int dataGenerationRuns = processCounterService.getSuccessCount("generateStudentData");
            int studentUploadRuns = processCounterService.getSuccessCount("processAndSaveExcelFile");
            StudentStatsResponse stats = new StudentStatsResponse(existingStudents, dataProcessRuns, dataGenerationRuns, studentUploadRuns);

            return EntityResponse.builder()
                    .statusCode(HttpStatus.OK.value())
                    .message("Student(s) fetched successfully")
                    .entity(stats)
                    .pagination(pagination)
                    .build();

        } catch (Exception e) {
            logger.error("Error in fetching students: ", e);
            return commonErrors.error500();
        }
    }

    public EntityResponse<?> updateStudent(Student incomingStudent) {
        try {
            Optional<Student> existingStudent = studentRepository.findById(incomingStudent.getStudentId());

            if (existingStudent.isEmpty()) {
                return commonErrors.error400("Student with ID: " + incomingStudent.getStudentId() + " does not exist");
            } else {
                incomingStudent.setModifiedFlag('Y');
                incomingStudent.setModifiedBy("");
                incomingStudent.setModifiedTime(LocalDateTime.now());

                studentRepository.save(incomingStudent);

                return EntityResponse.builder()
                        .message("Student updated successfully")
                        .entity(incomingStudent)
                        .statusCode(HttpStatus.OK.value())
                        .build();
            }
        } catch (Exception e) {
            logger.error("Error in updating student: ", e);
           return commonErrors.error500();
            }
    }

    public EntityResponse<?> deleteStudent(Student incomingStudent) {
        try {
            Optional<Student> existingStudent = studentRepository.findById(incomingStudent.getStudentId());

            if (existingStudent.isEmpty()) {
                return commonErrors.error400("Student with ID: " + incomingStudent.getStudentId() + " does not exist");
            } else {
                incomingStudent.setDeletedBy("");
                incomingStudent.setDeletedFlag('Y');
                incomingStudent.setDeletedTime(LocalDateTime.now());
                incomingStudent.setStatus(0);

                return commonSuccess.empty200();
            }
        } catch (Exception e) {
            logger.error("Error deleting student with ID: " + incomingStudent.getStudentId(), e);
            return commonErrors.error500();
        }
    }
}
