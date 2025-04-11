package com.indigointelligence.stackademics.DataGeneration;

import java.time.LocalDate;

public record StudentRecord(
        long studentId,
        String firstName,
        String lastName,
        LocalDate dob,
        String className,
        int score,
        int status,
        String photoPath
) {}
