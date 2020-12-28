package com.foxminded.university.controllers.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.foxminded.university.dto.courses.CourseDTORequest;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Sql(value = "/create_tables_for_courses.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class CoursesRestControllerSystemTest {
    @Autowired
    private WebApplicationContext context;
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Before
    public void init() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void showAllCourses_shouldRetrieveAllCoursesFromDB() throws Exception {
        mockMvc.perform(get("/api/courses/"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()", Matchers.is(4)))
                .andExpect(jsonPath("$[0].courseId", Matchers.is(1)))
                .andExpect(jsonPath("$[0].courseName", Matchers.is("History")))
                .andExpect(jsonPath("$[0].description", Matchers.is("History Description")))
                .andExpect(jsonPath("$[1].courseId", Matchers.is(2)))
                .andExpect(jsonPath("$[1].courseName", Matchers.is("Robotics")))
                .andExpect(jsonPath("$[1].description", Matchers.is("Robotics Description")))
                .andExpect(jsonPath("$[2].courseId", Matchers.is(3)))
                .andExpect(jsonPath("$[2].courseName", Matchers.is("Criminology")))
                .andExpect(jsonPath("$[2].description", Matchers.is("Criminology Description")))
                .andExpect(jsonPath("$[3].courseId", Matchers.is(4)))
                .andExpect(jsonPath("$[3].courseName", Matchers.is("Architecture")))
                .andExpect(jsonPath("$[3].description", Matchers.is("Architecture Description")));
    }

    @Test
    public void showCourses_shouldThrowExceptionOnBadRequest() throws Exception {
        mockMvc.perform(get("/api/course")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void showCourseByID_shouldRetrieveCourseFromDBByID() throws Exception {
        mockMvc.perform(get("/api/courses/3"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.courseId", Matchers.is(3)))
                .andExpect(jsonPath("$.courseName", Matchers.is("Criminology")))
                .andExpect(jsonPath("$.description", Matchers.is("Criminology Description")));
    }

    @Test
    public void showCourseByID_shouldThrowException() throws Exception {
        mockMvc.perform(get("/api/courses/{course_id}", "bad_request")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void showStudentsByCourse_shouldRetrieveStudentsByCourseFromDB() throws Exception {
        mockMvc.perform(get("/api/courses/3/students"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()", Matchers.is(3)))
                .andExpect(jsonPath("$[0].studentId", Matchers.is(1)))
                .andExpect(jsonPath("$[0].firstName", Matchers.is("Alex")))
                .andExpect(jsonPath("$[0].lastName", Matchers.is("Smith")))
                .andExpect(jsonPath("$[0].groupDTOResponse.groupId", Matchers.is(1)))
                .andExpect(jsonPath("$[0].groupDTOResponse.groupName", Matchers.is("aa-01")))
                .andExpect(jsonPath("$[1].studentId", Matchers.is(2)))
                .andExpect(jsonPath("$[1].firstName", Matchers.is("Ann")))
                .andExpect(jsonPath("$[1].lastName", Matchers.is("White")))
                .andExpect(jsonPath("$[1].groupDTOResponse.groupId", Matchers.is(2)))
                .andExpect(jsonPath("$[1].groupDTOResponse.groupName", Matchers.is("aa-02")))
                .andExpect(jsonPath("$[2].studentId", Matchers.is(3)))
                .andExpect(jsonPath("$[2].firstName", Matchers.is("Leo")))
                .andExpect(jsonPath("$[2].lastName", Matchers.is("Messi")))
                .andExpect(jsonPath("$[2].groupDTOResponse.groupId", Matchers.is(2)))
                .andExpect(jsonPath("$[2].groupDTOResponse.groupName", Matchers.is("aa-02")));
    }

    @Test
    public void saveCourse_shouldAddNewCourseToDBAndReturnIt() throws Exception {
        CourseDTORequest courseDTORequest = new CourseDTORequest();
        courseDTORequest.setCourseName("New Course");
        courseDTORequest.setDescription("New Course Description");
        courseDTORequest.setProfessorId(3);

        mockMvc.perform(post("/api/courses/")
                .content(objectMapper.writeValueAsString(courseDTORequest))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.courseId", Matchers.is(5)))
                .andExpect(jsonPath("$.courseName", Matchers.is("New Course")))
                .andExpect(jsonPath("$.description", Matchers.is("New Course Description")))
                .andExpect(jsonPath("$.professorDTOResponse.professorId", Matchers.is(3)))
                .andExpect(jsonPath("$.professorDTOResponse.firstName", Matchers.is("Robert")))
                .andExpect(jsonPath("$.professorDTOResponse.lastName", Matchers.is("Martin")));
    }

    @Test
    public void saveCourse_shouldThrowExceptionWhenRequestedWithInvalidParameters() throws Exception {
        CourseDTORequest courseDTORequest = new CourseDTORequest();
        courseDTORequest.setCourseName("");
        courseDTORequest.setDescription("New Course Description");
        courseDTORequest.setProfessorId(3);

        mockMvc.perform(post("/api/courses/")
                .content(objectMapper.writeValueAsString(courseDTORequest))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void updateCourse_shouldUpdateCourseByIDInDBAndReturnIt() throws Exception {
        CourseDTORequest courseDTORequest = new CourseDTORequest();
        courseDTORequest.setCourseName("New Course");
        courseDTORequest.setDescription("New Course Description");
        courseDTORequest.setProfessorId(1);

        mockMvc.perform(patch("/api/courses/2")
                .content(objectMapper.writeValueAsString(courseDTORequest))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.courseId", Matchers.is(2)))
                .andExpect(jsonPath("$.courseName", Matchers.is("New Course")))
                .andExpect(jsonPath("$.description", Matchers.is("New Course Description")))
                .andExpect(jsonPath("$.professorDTOResponse.professorId", Matchers.is(1)))
                .andExpect(jsonPath("$.professorDTOResponse.firstName", Matchers.is("Gerbert")))
                .andExpect(jsonPath("$.professorDTOResponse.lastName", Matchers.is("Shildt")));
    }

    @Test
    public void updateCourse_shouldThrowExceptionWhenRequestedWithInvalidParameters() throws Exception {
        CourseDTORequest courseDTORequest = new CourseDTORequest();
        courseDTORequest.setCourseName("New Course");
        courseDTORequest.setDescription("N");
        courseDTORequest.setProfessorId(3);

        mockMvc.perform(post("/api/courses/")
                .content(objectMapper.writeValueAsString(courseDTORequest))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void deleteCourse_shouldDeleteCourseFromDB() throws Exception {
        mockMvc.perform(delete("/api/courses/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteCourse_shouldThrowExceptionOnBadRequest() throws Exception {
        mockMvc.perform(delete("/api/courses/courseID"))
                .andExpect(status().isBadRequest());
    }
}

