package com.foxminded.university;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Lesson {
    private Professor professor;
    private Course course;
    private List<Group> groups;
    private ClassRoom classRoom;
    private LessonTime time;

    public Lesson() {
        groups = new ArrayList<>();
    }

    public Lesson(Professor professor, Course course, ClassRoom classRoom, LessonTime time) {
        this.professor = professor;
        this.course = course;
        this.classRoom = classRoom;
        this.time = time;
        groups = new ArrayList<>();
    }

    public void addGroup(Group group) {
        groups.add(group);
    }

    public void addGroups(Group ... group) {
        groups.addAll(Arrays.asList(group));
    }

    public void removeGroup(Group group) {
        groups.remove(group);
    }

    public Professor getProfessor() {
        return professor;
    }

    public void setProfessor(Professor professor) {
        this.professor = professor;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public ClassRoom getClassRoom() {
        return classRoom;
    }

    public void setClassRoom(ClassRoom classRoom) {
        this.classRoom = classRoom;
    }

    public LessonTime getTime() {
        return time;
    }

    public void setTime(LessonTime time) {
        this.time = time;
    }

    public List<Group> getGroups() {
        return groups;
    }
}
