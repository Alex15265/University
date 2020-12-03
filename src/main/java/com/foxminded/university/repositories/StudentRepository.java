package com.foxminded.university.repositories;

import com.foxminded.university.entities.Student;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface StudentRepository extends CrudRepository<Student, Integer> {
    List<Student> findByOrderByStudentIdAsc();
}
