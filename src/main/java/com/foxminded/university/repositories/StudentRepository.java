package com.foxminded.university.repositories;

import com.foxminded.university.entities.Student;
import org.springframework.data.repository.CrudRepository;

public interface StudentRepository extends CrudRepository<Student, Integer> {
}
