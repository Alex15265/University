package com.foxminded.university;

public class Student {
    private Integer studentID;
    private String name;
    private String lastName;

    public Student() {
    }

    public Student(Integer studentID, String name, String lastName) {
        this.studentID = studentID;
        this.name = name;
        this.lastName = lastName;
    }

    public Integer getStudentID() {
        return studentID;
    }

    public String getName() {
        return name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setStudentID(Integer studentID) {
        this.studentID = studentID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
