package com.foxminded.university;

import java.util.ArrayList;
import java.util.List;

public class Professor {
    private Integer professorID;
    private String name;
    private String lastName;
    private List<Course> courses;

    public Professor() {
        courses = new ArrayList<>();
    }

    public Professor(Integer professorID, String name, String lastName) {
        this.professorID = professorID;
        this.name = name;
        this.lastName = lastName;
        courses = new ArrayList<>();
    }

    public void addCourse(Course course) {
        courses.add(course);
    }

    public void removeCourse(Course course) {
        courses.remove(course);
    }

    public Integer getProfessorID() {
        return professorID;
    }

    public void setProfessorID(Integer professorID) {
        this.professorID = professorID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<Course> getCourses() {
        return courses;
    }
}
