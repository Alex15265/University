package com.foxminded.university.repositories;

import com.foxminded.university.entities.Professor;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProfessorRepository extends CrudRepository<Professor, Integer> {
    List<Professor> findByOrderByProfessorIdAsc();
}
