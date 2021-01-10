package com.foxminded.university.entities;

import lombok.Data;
import javax.persistence.*;
import javax.validation.constraints.*;

@Data
@Entity
@Table(name = "classrooms")
public class ClassRoom {
    @Id
    @Column(name = "room_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer roomId;
    @Column(name = "room_number")
    @Min(value = 100, message = "Room Number cannot be less than 100") @NotNull(message = "This field cannot be empty")
    private Integer roomNumber;
}
