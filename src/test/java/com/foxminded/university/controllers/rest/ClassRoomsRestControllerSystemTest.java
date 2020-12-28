package com.foxminded.university.controllers.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.foxminded.university.dto.classRoom.ClassRoomDTORequest;
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
@Sql(value = "/create_tables_for_classrooms.sql",
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class ClassRoomsRestControllerSystemTest {
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
    public void showAllClassRooms_shouldRetrieveAllClassRoomsFromDB() throws Exception {
        mockMvc.perform(get("/api/classrooms/"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()", Matchers.is(3)))
                .andExpect(jsonPath("$[0].roomId", Matchers.is(1)))
                .andExpect(jsonPath("$[0].roomNumber", Matchers.is(101)))
                .andExpect(jsonPath("$[1].roomId", Matchers.is(2)))
                .andExpect(jsonPath("$[1].roomNumber", Matchers.is(102)))
                .andExpect(jsonPath("$[2].roomId", Matchers.is(3)))
                .andExpect(jsonPath("$[2].roomNumber", Matchers.is(103)));
    }

    @Test
    public void showClassRooms_shouldThrowExceptionOnBadRequest() throws Exception {
        mockMvc.perform(get("/api/classroo")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void showClassRoomByID_shouldRetrieveClassRoomFromDBByID() throws Exception {
        mockMvc.perform(get("/api/classrooms/2"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.roomId", Matchers.is(2)))
                .andExpect(jsonPath("$.roomNumber", Matchers.is(102)));
    }

    @Test
    public void showClassRoomByID_shouldThrowExceptionOnBadRequest() throws Exception {
        mockMvc.perform(get("/api/classrooms/{room_id}", "bad_request")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void saveClassRoom_shouldAddNewClassRoomToDBAndReturnIt() throws Exception {
        ClassRoomDTORequest classRoomDTORequest = new ClassRoomDTORequest();
        classRoomDTORequest.setRoomNumber(777);

        mockMvc.perform(post("/api/classrooms/")
                .content(objectMapper.writeValueAsString(classRoomDTORequest))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.roomId", Matchers.is(4)))
                .andExpect(jsonPath("$.roomNumber", Matchers.is(777)));
    }

    @Test
    public void saveClassRoom_shouldThrowExceptionWhenRequestedWithInvalidParameters() throws Exception {
        ClassRoomDTORequest classRoomDTORequest = new ClassRoomDTORequest();
        classRoomDTORequest.setRoomNumber(-5);

        mockMvc.perform(post("/api/classrooms/")
                .content(objectMapper.writeValueAsString(classRoomDTORequest))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void updateClassRoom_shouldUpdateClassRoomByIDInDBAndReturnIt() throws Exception {
        ClassRoomDTORequest classRoomDTORequest = new ClassRoomDTORequest();
        classRoomDTORequest.setRoomNumber(777);

        mockMvc.perform(patch("/api/classrooms/1")
                .content(objectMapper.writeValueAsString(classRoomDTORequest))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.roomId", Matchers.is(1)))
                .andExpect(jsonPath("$.roomNumber", Matchers.is(777)));
    }

    @Test
    public void updateClassRoom_shouldThrowExceptionWhenRequestedWithInvalidParameters() throws Exception {
        ClassRoomDTORequest classRoomDTORequest = new ClassRoomDTORequest();
        classRoomDTORequest.setRoomNumber(-5);

        mockMvc.perform(patch("/api/classrooms/2")
                .content(objectMapper.writeValueAsString(classRoomDTORequest))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void deleteClassRoom_shouldDeleteClassRoomFromDB() throws Exception {
        mockMvc.perform(delete("/api/classrooms/3"))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteClassRoom_shouldThrowExceptionOnBadRequest() throws Exception {
        mockMvc.perform(delete("/api/classrooms/roomNumber"))
                .andExpect(status().isBadRequest());
    }
}
