package com.foxminded.university.controllers.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.foxminded.university.dto.professor.ProfessorDTORequest;
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
@Sql(value = "/create_tables_for_professors.sql",
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class ProfessorsRestControllerSystemTest {
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
    public void showAllProfessors_shouldRetrieveAllProfessorsFromDB() throws Exception {
        mockMvc.perform(get("/api/professors/"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()", Matchers.is(3)))
                .andExpect(jsonPath("$[0].professorId", Matchers.is(1)))
                .andExpect(jsonPath("$[0].firstName", Matchers.is("Gerbert")))
                .andExpect(jsonPath("$[0].lastName", Matchers.is("Shildt")))
                .andExpect(jsonPath("$[1].professorId", Matchers.is(2)))
                .andExpect(jsonPath("$[1].firstName", Matchers.is("Albert")))
                .andExpect(jsonPath("$[1].lastName", Matchers.is("Einshtein")))
                .andExpect(jsonPath("$[2].professorId", Matchers.is(3)))
                .andExpect(jsonPath("$[2].firstName", Matchers.is("Robert")))
                .andExpect(jsonPath("$[2].lastName", Matchers.is("Martin")));
    }

    @Test
    public void showAllProfessors_shouldThrowExceptionOnBadRequest() throws Exception {
        mockMvc.perform(get("/api/professor")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void showProfessorByID_shouldRetrieveProfessorFromDBByID() throws Exception {
        mockMvc.perform(get("/api/professors/2"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.professorId", Matchers.is(2)))
                .andExpect(jsonPath("$.firstName", Matchers.is("Albert")))
                .andExpect(jsonPath("$.lastName", Matchers.is("Einshtein")));
    }

    @Test
    public void showProfessorByID_shouldThrowException() throws Exception {
        mockMvc.perform(get("/api/professors/{professor_id}", "bad_request")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void showCoursesByProfessor_shouldRetrieveCoursesByProfessorFromDB() throws Exception {
        mockMvc.perform(get("/api/professors/3/courses"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()", Matchers.is(2)))
                .andExpect(jsonPath("$[0].courseId", Matchers.is(3)))
                .andExpect(jsonPath("$[0].courseName", Matchers.is("Criminology")))
                .andExpect(jsonPath("$[0].description", Matchers.is("Criminology Description")))
                .andExpect(jsonPath("$[1].courseId", Matchers.is(4)))
                .andExpect(jsonPath("$[1].courseName", Matchers.is("Architecture")))
                .andExpect(jsonPath("$[1].description", Matchers.is("Architecture Description")));
    }

    @Test
    public void saveProfessor_shouldAddNewProfessorToDBAndReturnIt() throws Exception {
        ProfessorDTORequest professorDTORequest = new ProfessorDTORequest();
        professorDTORequest.setFirstName("Homer");
        professorDTORequest.setLastName("Simpson");

        mockMvc.perform(post("/api/professors/")
                .content(objectMapper.writeValueAsString(professorDTORequest))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.professorId", Matchers.is(4)))
                .andExpect(jsonPath("$.firstName", Matchers.is("Homer")))
                .andExpect(jsonPath("$.lastName", Matchers.is("Simpson")));
    }

    @Test
    public void saveProfessor_shouldThrowExceptionWhenRequestedWithInvalidParameters() throws Exception {
        ProfessorDTORequest professorDTORequest = new ProfessorDTORequest();
        professorDTORequest.setFirstName("");
        professorDTORequest.setLastName("Simpson");

        mockMvc.perform(post("/api/professors/")
                .content(objectMapper.writeValueAsString(professorDTORequest))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void updateProfessor_shouldUpdateProfessorByIDInDBAndReturnIt() throws Exception {
        ProfessorDTORequest professorDTORequest = new ProfessorDTORequest();
        professorDTORequest.setFirstName("Homer");
        professorDTORequest.setLastName("Simpson");

        mockMvc.perform(patch("/api/professors/2")
                .content(objectMapper.writeValueAsString(professorDTORequest))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.professorId", Matchers.is(2)))
                .andExpect(jsonPath("$.firstName", Matchers.is("Homer")))
                .andExpect(jsonPath("$.lastName", Matchers.is("Simpson")));
    }

    @Test
    public void updateProfessor_shouldThrowExceptionWhenRequestedWithInvalidParameters() throws Exception {
        ProfessorDTORequest professorDTORequest = new ProfessorDTORequest();
        professorDTORequest.setFirstName("Homer");
        professorDTORequest.setLastName("");

        mockMvc.perform(patch("/api/professors/")
                .content(objectMapper.writeValueAsString(professorDTORequest))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void deleteStudent_shouldDeleteStudentFromDB() throws Exception {
        mockMvc.perform(delete("/api/professors/2"))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteProfessor_shouldThrowExceptionOnBadRequest() throws Exception {
        mockMvc.perform(delete("/api/professors/profID"))
                .andExpect(status().isBadRequest());
    }
}
