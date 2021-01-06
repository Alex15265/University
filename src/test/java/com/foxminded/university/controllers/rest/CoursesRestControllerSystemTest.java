package com.foxminded.university.controllers.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.foxminded.university.dto.courses.CourseDTORequest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.is;
import static org.springframework.http.MediaType.APPLICATION_JSON;
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
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.size()", is(4)))
                .andExpect(jsonPath("$[0].courseId", is(1)))
                .andExpect(jsonPath("$[0].courseName", is("History")))
                .andExpect(jsonPath("$[0].description", is("History Description")))
                .andExpect(jsonPath("$[1].courseId", is(2)))
                .andExpect(jsonPath("$[1].courseName", is("Robotics")))
                .andExpect(jsonPath("$[1].description", is("Robotics Description")))
                .andExpect(jsonPath("$[2].courseId", is(3)))
                .andExpect(jsonPath("$[2].courseName", is("Criminology")))
                .andExpect(jsonPath("$[2].description", is("Criminology Description")))
                .andExpect(jsonPath("$[3].courseId", is(4)))
                .andExpect(jsonPath("$[3].courseName", is("Architecture")))
                .andExpect(jsonPath("$[3].description", is("Architecture Description")));
    }

    @Test
    public void showCourses_shouldThrowExceptionOnBadRequest() throws Exception {
        mockMvc.perform(get("/api/course")
                .contentType(APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void showCourseByID_shouldRetrieveCourseFromDBByID() throws Exception {
        mockMvc.perform(get("/api/courses/3"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.courseId", is(3)))
                .andExpect(jsonPath("$.courseName", is("Criminology")))
                .andExpect(jsonPath("$.description", is("Criminology Description")));
    }

    @Test
    public void showCourseByID_shouldThrowException() throws Exception {
        mockMvc.perform(get("/api/courses/{course_id}", "bad_request")
                .contentType(APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void showStudentsByCourse_shouldRetrieveStudentsByCourseFromDB() throws Exception {
        mockMvc.perform(get("/api/courses/3/students"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.size()", is(3)))
                .andExpect(jsonPath("$[0].studentId", is(1)))
                .andExpect(jsonPath("$[0].firstName", is("Alex")))
                .andExpect(jsonPath("$[0].lastName", is("Smith")))
                .andExpect(jsonPath("$[0].groupDTOResponse.groupId", is(1)))
                .andExpect(jsonPath("$[0].groupDTOResponse.groupName", is("aa-01")))
                .andExpect(jsonPath("$[1].studentId", is(2)))
                .andExpect(jsonPath("$[1].firstName", is("Ann")))
                .andExpect(jsonPath("$[1].lastName", is("White")))
                .andExpect(jsonPath("$[1].groupDTOResponse.groupId", is(2)))
                .andExpect(jsonPath("$[1].groupDTOResponse.groupName", is("aa-02")))
                .andExpect(jsonPath("$[2].studentId", is(3)))
                .andExpect(jsonPath("$[2].firstName", is("Leo")))
                .andExpect(jsonPath("$[2].lastName", is("Messi")))
                .andExpect(jsonPath("$[2].groupDTOResponse.groupId", is(2)))
                .andExpect(jsonPath("$[2].groupDTOResponse.groupName", is("aa-02")));
    }

    @Test
    public void saveCourse_shouldAddNewCourseToDBAndReturnIt() throws Exception {
        CourseDTORequest courseDTORequest = new CourseDTORequest();
        courseDTORequest.setCourseName("New Course");
        courseDTORequest.setDescription("New Course Description");
        courseDTORequest.setProfessorId(3);

        mockMvc.perform(post("/api/courses/")
                .content(objectMapper.writeValueAsString(courseDTORequest))
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.courseId", is(5)))
                .andExpect(jsonPath("$.courseName", is("New Course")))
                .andExpect(jsonPath("$.description", is("New Course Description")))
                .andExpect(jsonPath("$.professorDTOResponse.professorId", is(3)))
                .andExpect(jsonPath("$.professorDTOResponse.firstName", is("Robert")))
                .andExpect(jsonPath("$.professorDTOResponse.lastName", is("Martin")));
    }

    @Test
    public void saveCourse_shouldThrowExceptionWhenRequestedWithInvalidParameters() throws Exception {
        CourseDTORequest courseDTORequest = new CourseDTORequest();
        courseDTORequest.setCourseName("");
        courseDTORequest.setDescription("New Course Description");
        courseDTORequest.setProfessorId(3);

        mockMvc.perform(post("/api/courses/")
                .content(objectMapper.writeValueAsString(courseDTORequest))
                .contentType(APPLICATION_JSON))
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
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.courseId", is(2)))
                .andExpect(jsonPath("$.courseName", is("New Course")))
                .andExpect(jsonPath("$.description", is("New Course Description")))
                .andExpect(jsonPath("$.professorDTOResponse.professorId", is(1)))
                .andExpect(jsonPath("$.professorDTOResponse.firstName", is("Gerbert")))
                .andExpect(jsonPath("$.professorDTOResponse.lastName", is("Shildt")));
    }

    @Test
    public void updateCourse_shouldThrowExceptionWhenRequestedWithInvalidParameters() throws Exception {
        CourseDTORequest courseDTORequest = new CourseDTORequest();
        courseDTORequest.setCourseName("New Course");
        courseDTORequest.setDescription("N");
        courseDTORequest.setProfessorId(3);

        mockMvc.perform(post("/api/courses/")
                .content(objectMapper.writeValueAsString(courseDTORequest))
                .contentType(APPLICATION_JSON))
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

