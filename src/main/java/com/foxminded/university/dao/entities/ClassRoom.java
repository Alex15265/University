package com.foxminded.university.dao.entities;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "classrooms")
public class ClassRoom {
    @Id
    @Column(name = "room_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer roomId;
    @Column(name = "room_number")
    private Integer roomNumber;
}
