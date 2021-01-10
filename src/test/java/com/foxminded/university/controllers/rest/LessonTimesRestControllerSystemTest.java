package com.foxminded.university.controllers.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.foxminded.university.dto.lessonTime.LessonTimeDTORequest;
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
@Sql(value = "/create_tables_for_lessontimes.sql",
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class LessonTimesRestControllerSystemTest {
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
    public void showAllLessonTimes_shouldRetrieveAllLessonTimesFromDB() throws Exception {
        mockMvc.perform(get("/api/lessontimes/"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.size()", is(4)))
                .andExpect(jsonPath("$[0].timeId", is(1)))
                .andExpect(jsonPath("$[0].lessonStart", is("2020-09-05T08:00:00")))
                .andExpect(jsonPath("$[0].lessonEnd", is("2020-09-05T09:30:00")))
                .andExpect(jsonPath("$[1].timeId", is(2)))
                .andExpect(jsonPath("$[1].lessonStart", is("2020-09-05T09:45:00")))
                .andExpect(jsonPath("$[1].lessonEnd", is("2020-09-05T11:15:00")))
                .andExpect(jsonPath("$[2].timeId", is(3)))
                .andExpect(jsonPath("$[2].lessonStart", is("2020-09-14T09:45:00")))
                .andExpect(jsonPath("$[2].lessonEnd", is("2020-09-14T11:15:00")))
                .andExpect(jsonPath("$[3].timeId", is(4)))
                .andExpect(jsonPath("$[3].lessonStart", is("2020-09-14T11:30:00")))
                .andExpect(jsonPath("$[3].lessonEnd", is("2020-09-14T13:00:00")));
    }

    @Test
    public void showAllLessonTimes_shouldThrowExceptionOnBadRequest() throws Exception {
        mockMvc.perform(get("/api/lessonsss")
                .contentType(APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void showLessonTimeByID_shouldRetrieveLessonTimeFromDBByID() throws Exception {
        mockMvc.perform(get("/api/lessontimes/3"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.timeId", is(3)))
                .andExpect(jsonPath("$.lessonStart", is("2020-09-14T09:45:00")))
                .andExpect(jsonPath("$.lessonEnd", is("2020-09-14T11:15:00")));
    }

    @Test
    public void showLessonTimeByID_shouldThrowException() throws Exception {
        mockMvc.perform(get("/api/lessontimes/{time_id}", "bad_request")
                .contentType(APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void saveLessonTime_shouldAddNewLessonTimeToDBAndReturnIt() throws Exception {
        LessonTimeDTORequest lessonTimeDTORequest = new LessonTimeDTORequest();
        lessonTimeDTORequest.setLessonStart("01.01.22 08:00");
        lessonTimeDTORequest.setLessonEnd("01.01.22 09:00");

        mockMvc.perform(post("/api/lessontimes/")
                .content(objectMapper.writeValueAsString(lessonTimeDTORequest))
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.timeId", is(5)))
                .andExpect(jsonPath("$.lessonStart", is("2022-01-01T08:00:00")))
                .andExpect(jsonPath("$.lessonEnd", is("2022-01-01T09:00:00")));
    }

    @Test
    public void saveLessonTime_shouldThrowExceptionWhenRequestedWithInvalidParameters() throws Exception {
        LessonTimeDTORequest lessonTimeDTORequest = new LessonTimeDTORequest();
        lessonTimeDTORequest.setLessonStart("01.01.2022 08:00");
        lessonTimeDTORequest.setLessonEnd("01.01.2022 09:00");

        mockMvc.perform(post("/api/lessontimes/")
                .content(objectMapper.writeValueAsString(lessonTimeDTORequest))
                .contentType(APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void updateLessonTime_shouldUpdateLessonTimeByIDInDBAndReturnIt() throws Exception {
        LessonTimeDTORequest lessonTimeDTORequest = new LessonTimeDTORequest();
        lessonTimeDTORequest.setLessonStart("01.01.22 08:00");
        lessonTimeDTORequest.setLessonEnd("01.01.22 09:00");

        mockMvc.perform(patch("/api/lessontimes/4")
                .content(objectMapper.writeValueAsString(lessonTimeDTORequest))
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.timeId", is(4)))
                .andExpect(jsonPath("$.lessonStart", is("2022-01-01T08:00:00")))
                .andExpect(jsonPath("$.lessonEnd", is("2022-01-01T09:00:00")));
    }

    @Test
    public void updateLessonTime_shouldThrowExceptionWhenRequestedWithInvalidParameters() throws Exception {
        LessonTimeDTORequest lessonTimeDTORequest = new LessonTimeDTORequest();
        lessonTimeDTORequest.setLessonStart("01.01.2022 08:00");
        lessonTimeDTORequest.setLessonEnd("01.01.2022 09:00");

        mockMvc.perform(patch("/api/lessontimes/")
                .content(objectMapper.writeValueAsString(lessonTimeDTORequest))
                .contentType(APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void deleteLessonTime_shouldDeleteLessonTimeFromDB() throws Exception {
        mockMvc.perform(delete("/api/lessontimes/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteLessonTime_shouldThrowExceptionOnBadRequest() throws Exception {
        mockMvc.perform(delete("/api/lessontimes/lessontimeID"))
                .andExpect(status().isBadRequest());
    }
}

