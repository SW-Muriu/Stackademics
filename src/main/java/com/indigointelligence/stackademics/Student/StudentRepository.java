package com.indigointelligence.stackademics.Student;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    @Query("""
    SELECT s FROM Student s WHERE s.status = :status AND (LOWER(s.firstName) LIKE LOWER(CONCAT('%', :searchTerm, '%'))\s
     OR LOWER(s.lastName) LIKE LOWER(CONCAT('%', :searchTerm, '%')))
""")
    Page<Student> searchByStatusAndName(@Param("status") int status, @Param("searchTerm") String searchTerm, Pageable pageable);


    Page<Student> findByStatus(int status, Pageable pageable);
}
