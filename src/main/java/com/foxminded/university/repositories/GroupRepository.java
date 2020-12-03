package com.foxminded.university.repositories;

import com.foxminded.university.entities.Group;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface GroupRepository extends CrudRepository<Group, Integer> {
    List<Group> findByOrderByGroupIdAsc();
    Group findByGroupName(String groupName);
}
