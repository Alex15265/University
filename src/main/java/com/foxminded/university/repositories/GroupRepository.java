package com.foxminded.university.repositories;

import com.foxminded.university.entities.Group;
import org.springframework.data.repository.CrudRepository;

public interface GroupRepository extends CrudRepository<Group, Integer> {
}
