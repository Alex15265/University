package com.foxminded.university;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    public List<Professor> readAllProfessors() {
        return professors;
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

    public List<Student> readAllStudents() {
        return students;
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

    public List<Course> readAllCourses() {
        return courses;
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

    public List<Group> readAllGroups() {
        return groups;
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

    public List<ClassRoom> readAllClassRooms() {
        return classRooms;
    }

    public Timetable readProfessorTimetableForADay(Professor professor, LocalDateTime date) {
        Timetable professorTimetableForADay = new Timetable();
        for(Lesson lesson: timetable.getListOfLessons()) {
            if(lesson.getProfessor().equals(professor) && lesson.getTime().getLessonStart().compareTo(date) == 1
             && lesson.getTime().getLessonStart().compareTo(date.plusHours(24)) == -1) {
                professorTimetableForADay.addLesson(lesson);
            }
        }
        return professorTimetableForADay;
    }

    public Timetable readProfessorTimetableForAMonth(Professor professor, LocalDateTime date) {
        Timetable professorTimetableForAMonth = new Timetable();
        for(Lesson lesson: timetable.getListOfLessons()) {
            if(lesson.getProfessor().equals(professor) && lesson.getTime().getLessonStart().isAfter(date)
                    && lesson.getTime().getLessonStart().isBefore(date.plusMonths(1))) {
                professorTimetableForAMonth.addLesson(lesson);
            }
        }
        return professorTimetableForAMonth;
    }

    public Timetable readStudentTimetableForADay(Group group, Course course, LocalDateTime date) {
        Timetable studentTimetableForADay = new Timetable();
        for(Lesson lesson: timetable.getListOfLessons()) {
            if(lesson.getCourse().equals(course) && lesson.getTime().getLessonStart().compareTo(date) == 1
             && lesson.getTime().getLessonStart().compareTo(date.plusHours(24)) == -1) {
                for(Group g: lesson.getGroups()) {
                    if(g.equals(group)) {
                        studentTimetableForADay.addLesson(lesson);
                    }
                }
            }
        }
        return studentTimetableForADay;
    }

    public Timetable readStudentTimetableForAMonth(Group group, Course course, LocalDateTime date) {
        Timetable readStudentTimetableForAMonth = new Timetable();
        for(Lesson lesson: timetable.getListOfLessons()) {
            if(lesson.getCourse().equals(course) && lesson.getTime().getLessonStart().isAfter(date)
                    && lesson.getTime().getLessonStart().isBefore(date.plusMonths(1))) {
                for(Group g: lesson.getGroups()) {
                    if(g.equals(group)) {
                        readStudentTimetableForAMonth.addLesson(lesson);
                    }
                }
            }
        }
        return readStudentTimetableForAMonth;
    }

    public Timetable getTimetable() {
        return timetable;
    }
}
