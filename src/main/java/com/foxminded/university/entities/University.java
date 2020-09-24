package com.foxminded.university.entities;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class University {
    private List<Professor> professors;
    private List<Student> students;
    private List<Course> courses;
    private List<Group> groups;
    private List<ClassRoom> classRooms;
    private Timetable timetable;

    public University() {
        professors = new ArrayList<>();
        students = new ArrayList<>();
        courses = new ArrayList<>();
        groups = new ArrayList<>();
        classRooms = new ArrayList<>();
        timetable = new Timetable();
    }

    public void addProfessor(Professor professor) {
        professors.add(professor);
    }

    public void removeProfessor(Professor professor) {
        professors.remove(professor);
    }

    public Professor readProfessor(Integer professorID) {
        Professor professor = new Professor();
        for(Professor p: professors) {
            if (p.getProfessorID().equals(professorID)) professor = p;
        }
        return professor;
    }

    public void addStudent(Student student) {
        students.add(student);
    }

    public void removeStudent(Student student) {
        students.remove(student);
    }

    public Student readStudent(Integer studentID) {
        Student student = new Student();
        for(Student s: students) {
            if (s.getStudentID().equals(studentID)) student = s;
        }
        return student;
    }

    public void addCourse(Course course) {
        courses.add(course);
    }

    public void removeCourse(Course course) {
        courses.remove(course);
    }

    public Course readCourse(String courseName) {
        Course course = new Course();
        for(Course c: courses) {
            if (c.getCourseName().equals(courseName)) course = c;
        }
        return course;
    }

    public void addGroup(Group group) {
        groups.add(group);
    }

    public void removeGroup(Group group) {
        groups.remove(group);
    }

    public Group readGroup(String groupName) {
        Group group = new Group();
        for(Group g: groups) {
            if (g.getGroupName().equals(groupName)) group = g;
        }
        return group;
    }

    public void addClassRoom(ClassRoom classRoom) {
        classRooms.add(classRoom);
    }

    public void removeClassRoom(ClassRoom classRoom) {
        classRooms.remove(classRoom);
    }
}
