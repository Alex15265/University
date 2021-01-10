package com.foxminded.university.controllers.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.foxminded.university.dto.professor.ProfessorDTORequest;
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
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.size()", is(3)))
                .andExpect(jsonPath("$[0].professorId", is(1)))
                .andExpect(jsonPath("$[0].firstName", is("Gerbert")))
                .andExpect(jsonPath("$[0].lastName", is("Shildt")))
                .andExpect(jsonPath("$[1].professorId", is(2)))
                .andExpect(jsonPath("$[1].firstName", is("Albert")))
                .andExpect(jsonPath("$[1].lastName", is("Einshtein")))
                .andExpect(jsonPath("$[2].professorId", is(3)))
                .andExpect(jsonPath("$[2].firstName", is("Robert")))
                .andExpect(jsonPath("$[2].lastName", is("Martin")));
    }

    @Test
    public void showAllProfessors_shouldThrowExceptionOnBadRequest() throws Exception {
        mockMvc.perform(get("/api/professor")
                .contentType(APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void showProfessorByID_shouldRetrieveProfessorFromDBByID() throws Exception {
        mockMvc.perform(get("/api/professors/2"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.professorId", is(2)))
                .andExpect(jsonPath("$.firstName", is("Albert")))
                .andExpect(jsonPath("$.lastName", is("Einshtein")));
    }

    @Test
    public void showProfessorByID_shouldThrowException() throws Exception {
        mockMvc.perform(get("/api/professors/{professor_id}", "bad_request")
                .contentType(APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void showCoursesByProfessor_shouldRetrieveCoursesByProfessorFromDB() throws Exception {
        mockMvc.perform(get("/api/professors/3/courses"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.size()", is(2)))
                .andExpect(jsonPath("$[0].courseId", is(3)))
                .andExpect(jsonPath("$[0].courseName", is("Criminology")))
                .andExpect(jsonPath("$[0].description", is("Criminology Description")))
                .andExpect(jsonPath("$[1].courseId", is(4)))
                .andExpect(jsonPath("$[1].courseName", is("Architecture")))
                .andExpect(jsonPath("$[1].description", is("Architecture Description")));
    }

    @Test
    public void saveProfessor_shouldAddNewProfessorToDBAndReturnIt() throws Exception {
        ProfessorDTORequest professorDTORequest = new ProfessorDTORequest();
        professorDTORequest.setFirstName("Homer");
        professorDTORequest.setLastName("Simpson");

        mockMvc.perform(post("/api/professors/")
                .content(objectMapper.writeValueAsString(professorDTORequest))
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.professorId", is(4)))
                .andExpect(jsonPath("$.firstName", is("Homer")))
                .andExpect(jsonPath("$.lastName", is("Simpson")));
    }

    @Test
    public void saveProfessor_shouldThrowExceptionWhenRequestedWithInvalidParameters() throws Exception {
        ProfessorDTORequest professorDTORequest = new ProfessorDTORequest();
        professorDTORequest.setFirstName("");
        professorDTORequest.setLastName("Simpson");

        mockMvc.perform(post("/api/professors/")
                .content(objectMapper.writeValueAsString(professorDTORequest))
                .contentType(APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void updateProfessor_shouldUpdateProfessorByIDInDBAndReturnIt() throws Exception {
        ProfessorDTORequest professorDTORequest = new ProfessorDTORequest();
        professorDTORequest.setFirstName("Homer");
        professorDTORequest.setLastName("Simpson");

        mockMvc.perform(patch("/api/professors/2")
                .content(objectMapper.writeValueAsString(professorDTORequest))
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.professorId", is(2)))
                .andExpect(jsonPath("$.firstName", is("Homer")))
                .andExpect(jsonPath("$.lastName", is("Simpson")));
    }

    @Test
    public void updateProfessor_shouldThrowExceptionWhenRequestedWithInvalidParameters() throws Exception {
        ProfessorDTORequest professorDTORequest = new ProfessorDTORequest();
        professorDTORequest.setFirstName("Homer");
        professorDTORequest.setLastName("");

        mockMvc.perform(patch("/api/professors/")
                .content(objectMapper.writeValueAsString(professorDTORequest))
                .contentType(APPLICATION_JSON))
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
