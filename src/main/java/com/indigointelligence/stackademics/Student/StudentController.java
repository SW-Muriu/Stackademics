package com.indigointelligence.stackademics.Student;

import com.indigointelligence.stackademics.Utils.EntityResponse.EntityResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.sound.midi.Soundbank;
import java.io.IOException;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/student/")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @PostMapping("post")
    public EntityResponse<?> postStudent(@RequestBody Student student){
        return studentService.postStudent(student);
    }

    @GetMapping("all")
    public EntityResponse<?> getAllStudents(
            @RequestParam(required = false) Integer pageSize,
            @RequestParam(required = false) Integer pageIndex,
            @RequestParam(required = false) String searchTerm) {
        return studentService.fetchAll(pageSize, pageIndex, searchTerm);
    }

    @PutMapping("update")
    public EntityResponse<?> updateStudents(@RequestBody Student student) {
        return studentService.updateStudent(student);
    }

    @DeleteMapping("delete")
    public EntityResponse<?> deleteStudent(@RequestBody Student student){
        System.out.println(student.getStudentId());
        return studentService.deleteStudent(student);
    }

    @GetMapping("report")
    public ResponseEntity<byte[]> generateStudentReport() throws IOException {
        byte[] reportData = studentService.generateStudentReport();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", "students-report.xlsx");
        headers.setContentLength(reportData.length);

        return new ResponseEntity<>(reportData, headers, HttpStatus.OK);
    }
}
