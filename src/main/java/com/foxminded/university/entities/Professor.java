package com.foxminded.university.entities;

import lombok.Data;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@Entity
@Table(name = "professors")
public class Professor {
    @Id
    @Column(name = "professor_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer professorId;
    @Column(name = "first_name")
    @NotEmpty(message = "This field cannot be empty")
    private String firstName;
    @Column(name = "last_name")
    @NotEmpty(message = "This field cannot be empty")
    private String lastName;
    @OneToMany(mappedBy = "professor", fetch = FetchType.LAZY, cascade= {CascadeType.REMOVE})
    private List<Course> courses;
}
