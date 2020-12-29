package com.foxminded.university.controllers.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.foxminded.university.dto.student.StudentDTORequest;
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
@Sql(value = "/create_tables_for_students_and_groups.sql",
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class StudentsRestControllerSystemTest {
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
    public void showAllStudents_shouldRetrieveAllStudentsFromDB() throws Exception {
        mockMvc.perform(get("/api/students/"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.size()", is(6)))
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
                .andExpect(jsonPath("$[2].groupDTOResponse.groupName", is("aa-02")))
                .andExpect(jsonPath("$[3].studentId", is(4)))
                .andExpect(jsonPath("$[3].firstName", is("Lisa")))
                .andExpect(jsonPath("$[3].lastName", is("Ann")))
                .andExpect(jsonPath("$[3].groupDTOResponse.groupId", is(3)))
                .andExpect(jsonPath("$[3].groupDTOResponse.groupName", is("aa-03")))
                .andExpect(jsonPath("$[4].studentId", is(5)))
                .andExpect(jsonPath("$[4].firstName", is("Roy")))
                .andExpect(jsonPath("$[4].lastName", is("Jones")))
                .andExpect(jsonPath("$[4].groupDTOResponse.groupId", is(3)))
                .andExpect(jsonPath("$[4].groupDTOResponse.groupName", is("aa-03")))
                .andExpect(jsonPath("$[5].studentId", is(6)))
                .andExpect(jsonPath("$[5].firstName", is("Bart")))
                .andExpect(jsonPath("$[5].lastName", is("Simpson")))
                .andExpect(jsonPath("$[5].groupDTOResponse.groupId", is(3)))
                .andExpect(jsonPath("$[5].groupDTOResponse.groupName", is("aa-03")));
    }

    @Test
    public void showAllStudents_shouldThrowExceptionOnBadRequest() throws Exception {
        mockMvc.perform(get("/api/studentsss")
                .contentType(APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void showStudentByID_shouldRetrieveStudentFromDBByID() throws Exception {
        mockMvc.perform(get("/api/students/5"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.studentId", is(5)))
                .andExpect(jsonPath("$.firstName", is("Roy")))
                .andExpect(jsonPath("$.lastName", is("Jones")))
                .andExpect(jsonPath("$.groupDTOResponse.groupId", is(3)))
                .andExpect(jsonPath("$.groupDTOResponse.groupName", is("aa-03")));
    }

    @Test
    public void showStudentByID_shouldThrowException() throws Exception {
        mockMvc.perform(get("/api/students/{student_id}", "bad_request")
                .contentType(APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void saveStudent_shouldAddNewStudentToDBAndReturnIt() throws Exception {
        StudentDTORequest studentDTORequest = new StudentDTORequest();
        studentDTORequest.setFirstName("Till");
        studentDTORequest.setLastName("Lindemann");
        studentDTORequest.setGroupId(1);

        mockMvc.perform(post("/api/students/")
                .content(objectMapper.writeValueAsString(studentDTORequest))
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.studentId", is(7)))
                .andExpect(jsonPath("$.firstName", is("Till")))
                .andExpect(jsonPath("$.lastName", is("Lindemann")))
                .andExpect(jsonPath("$.groupDTOResponse.groupId", is(1)))
                .andExpect(jsonPath("$.groupDTOResponse.groupName", is("aa-01")));
    }

    @Test
    public void saveStudent_shouldThrowExceptionWhenRequestedWithInvalidParameters() throws Exception {
        StudentDTORequest studentDTORequest = new StudentDTORequest();
        studentDTORequest.setFirstName("Till");
        studentDTORequest.setLastName("Lindemann");
        studentDTORequest.setGroupId(-1);

        mockMvc.perform(post("/api/students/")
                .content(objectMapper.writeValueAsString(studentDTORequest))
                .contentType(APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void updateStudent_shouldUpdateStudentByIDInDBAndReturnIt() throws Exception {
        StudentDTORequest studentDTORequest = new StudentDTORequest();
        studentDTORequest.setFirstName("Richard");
        studentDTORequest.setLastName("Cruspe");
        studentDTORequest.setGroupId(2);

        mockMvc.perform(patch("/api/students/2")
                .content(objectMapper.writeValueAsString(studentDTORequest))
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.studentId", is(2)))
                .andExpect(jsonPath("$.firstName", is("Richard")))
                .andExpect(jsonPath("$.lastName", is("Cruspe")))
                .andExpect(jsonPath("$.groupDTOResponse.groupId", is(2)))
                .andExpect(jsonPath("$.groupDTOResponse.groupName", is("aa-02")));
    }

    @Test
    public void updateStudent_shouldThrowExceptionWhenRequestedWithInvalidParameters() throws Exception {
        StudentDTORequest studentDTORequest = new StudentDTORequest();
        studentDTORequest.setFirstName("Till");
        studentDTORequest.setLastName("");
        studentDTORequest.setGroupId(1);

        mockMvc.perform(patch("/api/students/")
                .content(objectMapper.writeValueAsString(studentDTORequest))
                .contentType(APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void deleteStudent_shouldDeleteStudentFromDB() throws Exception {
        mockMvc.perform(delete("/api/students/2"))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteStudent_shouldThrowExceptionOnBadRequest() throws Exception {
        mockMvc.perform(delete("/api/students/stID"))
                .andExpect(status().isBadRequest());
    }
}