package com.indigointelligence.stackademics.ProcessCounter;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class ProcessCounter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    private int successCount;

    public ProcessCounter(String name, int i) {
        this.name = name;
        this.successCount = i;
    }


    public void increment(){
        System.out.println("Counter Increment called: ");
        this.successCount++;
    }
}
