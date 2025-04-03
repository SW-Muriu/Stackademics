package com.indigointelligence.stackademics.Student;

import com.indigointelligence.stackademics.Utils.EntityResponse.EntityResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("get")
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
        return studentService.deleteStudent(student);
    }
}
