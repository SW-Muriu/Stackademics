package com.indigointelligence.stackademics.DataGeneration;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Random;


@Service
public class StudentDataGenerator {

    private static final Random random = new Random();
    private static final String[] CLASSES = {
            "Class1",
            "Class2",
            "Class3",
            "Class4",
            "Class5"
    };
    private static final LocalDate START_DATE = LocalDate.of(2010, 1, 1);
    private static final LocalDate END_DATE = LocalDate.of(2020, 12, 31);
    private static final long DAYS_BETWEEN = ChronoUnit.DAYS.between(START_DATE, END_DATE);
    private static final char[] ALPHABETS = "abcdefghijklmnopqrstuvwxyz".toCharArray();


    public StudentRecord generateStudent(long studentId) {
        return new StudentRecord(
                studentId,
                generateRandomName(3, 8),
                generateRandomName(3, 8),
                generateRandomDOB(),
                CLASSES[random.nextInt(CLASSES.length)],
                55 + random.nextInt(31),
                1,
                ""
        );
    }


    public String generateRandomName(int minLength, int maxLength) {
        int length = minLength + random.nextInt(maxLength - minLength + 1);
        char[] name = new char[length];

        for (int i = 0; i < length; i++) {
            name[i] = ALPHABETS[random.nextInt(ALPHABETS.length)];
        }
        name[0] = Character.toUpperCase(name[0]);
        return new String(name);
    }

    public LocalDate generateRandomDOB() {
        long randomDays = random.nextLong() % (DAYS_BETWEEN + 1);
        return START_DATE.plusDays(randomDays < 0 ? randomDays + DAYS_BETWEEN + 1 : randomDays);
    }
}


