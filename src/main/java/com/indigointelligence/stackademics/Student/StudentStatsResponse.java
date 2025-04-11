package com.indigointelligence.stackademics.Student;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class StudentStatsResponse {
    private List<Student> students;
    private int dataProcessRuns;
    private int dataGenerationRuns;
    private int studentUploadRuns;
}
