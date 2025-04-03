package com.indigointelligence.stackademics.Student;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    Page<Student> findByStatusAndFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(
            Integer status, String firstNameSearch, String lastNameSearch, Pageable pageable);

    Page<Student> findByStatus(int status, Pageable pageable);
}
