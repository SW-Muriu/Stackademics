package com.indigointelligence.stackademics.ProcessCounter;


import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProcessCounterService {

    @Autowired
    private ProcessCounterRepository counterRepository;

    @Transactional
    public void trackSuccess(String name) {

        ProcessCounter counter = counterRepository.findByName(name)
                .orElseGet(() -> new ProcessCounter(name, 0));
        counter.increment();
        counterRepository.save(counter);
    }

    public int getSuccessCount(String name) {
        return counterRepository.findByName(name)
                .map(ProcessCounter::getSuccessCount)
                .orElse(0);
    }
}
