package com.indigointelligence.stackademics.Student;

import com.indigointelligence.stackademics.Utils.AuditParameters.AuditParameters;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@Data
@Entity
@NoArgsConstructor
public class Student extends AuditParameters {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long studentId;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private LocalDate dob;

    @Column(name = "class", nullable = false)
    private String className;

    @Column(nullable = false)
    private Integer score;

    @Column(nullable = false)
    private Integer status = 1;

    @Column(nullable = true)
    private String photoPath = "";
}
