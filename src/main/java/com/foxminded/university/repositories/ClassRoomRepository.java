package com.foxminded.university.repositories;

import com.foxminded.university.entities.ClassRoom;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ClassRoomRepository extends CrudRepository<ClassRoom, Integer> {
    List<ClassRoom> findByOrderByRoomIdAsc();
    ClassRoom findByRoomNumber(Integer roomNumber);
}
