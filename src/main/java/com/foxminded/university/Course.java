package com.foxminded.university;

import java.util.ArrayList;
import java.util.List;

public class Course {
    private String courseName;
    private String description;
    private List<Student> students;

    public Course() {
        students = new ArrayList<>();
    }

    public Course(String courseName, String description) {
        this.courseName = courseName;
        this.description = description;
        students = new ArrayList<>();
    }

    public void addStudent(Student student) {
        students.add(student);
    }

    public void removeStudent(Student student) {
        students.remove(student);
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Student> getStudents() {
        return students;
    }
}
