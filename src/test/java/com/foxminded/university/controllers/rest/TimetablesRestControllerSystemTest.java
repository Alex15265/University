package com.foxminded.university.controllers.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Sql(value = "/create_tables_for_courses.sql",
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = "/create_tables_for_lessontimes.sql",
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = "/create_tables_for_classrooms.sql",
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = "/create_tables_for_lessons.sql",
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class TimetablesRestControllerSystemTest {
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
    public void findTimetableForProfessor_shouldRetrieve2LessonsFromDBForProfessor() throws Exception {
        mockMvc.perform(get("/api/professorTimetable/")
                .param("professorId", "3")
                .param("startTime", "01.09.20 07:00")
                .param("endTime", "01.10.20 07:00"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()", Matchers.is(2)))
                .andExpect(jsonPath("$[0].lessonId", Matchers.is(3)))
                .andExpect(jsonPath("$[0].professorDTOResponse.professorId", Matchers.is(3)))
                .andExpect(jsonPath("$[0].professorDTOResponse.firstName", Matchers.is("Robert")))
                .andExpect(jsonPath("$[0].professorDTOResponse.lastName", Matchers.is("Martin")))
                .andExpect(jsonPath("$[0].courseDTOResponse.courseId", Matchers.is(3)))
                .andExpect(jsonPath("$[0].courseDTOResponse.courseName", Matchers.is("Criminology")))
                .andExpect(jsonPath("$[0].courseDTOResponse.description", Matchers.is("Criminology Description")))
                .andExpect(jsonPath("$[0].classRoomDTOResponse.roomId", Matchers.is(3)))
                .andExpect(jsonPath("$[0].classRoomDTOResponse.roomNumber", Matchers.is(103)))
                .andExpect(jsonPath("$[0].lessonTimeDTOResponse.timeId", Matchers.is(1)))
                .andExpect(jsonPath("$[0].lessonTimeDTOResponse.lessonStart", Matchers.is("2020-09-05T08:00:00")))
                .andExpect(jsonPath("$[0].lessonTimeDTOResponse.lessonEnd", Matchers.is("2020-09-05T09:30:00")))
                .andExpect(jsonPath("$[0].groupDTOResponses.size()", Matchers.is(1)))
                .andExpect(jsonPath("$[0].groupDTOResponses.[0].groupId", Matchers.is(2)))
                .andExpect(jsonPath("$[0].groupDTOResponses.[0].groupName", Matchers.is("aa-02")))
                .andExpect(jsonPath("$[1].lessonId", Matchers.is(4)))
                .andExpect(jsonPath("$[1].professorDTOResponse.professorId", Matchers.is(3)))
                .andExpect(jsonPath("$[1].professorDTOResponse.firstName", Matchers.is("Robert")))
                .andExpect(jsonPath("$[1].professorDTOResponse.lastName", Matchers.is("Martin")))
                .andExpect(jsonPath("$[1].courseDTOResponse.courseId", Matchers.is(4)))
                .andExpect(jsonPath("$[1].courseDTOResponse.courseName", Matchers.is("Architecture")))
                .andExpect(jsonPath("$[1].courseDTOResponse.description", Matchers.is("Architecture Description")))
                .andExpect(jsonPath("$[1].classRoomDTOResponse.roomId", Matchers.is(3)))
                .andExpect(jsonPath("$[1].classRoomDTOResponse.roomNumber", Matchers.is(103)))
                .andExpect(jsonPath("$[1].lessonTimeDTOResponse.timeId", Matchers.is(2)))
                .andExpect(jsonPath("$[1].lessonTimeDTOResponse.lessonStart", Matchers.is("2020-09-05T09:45:00")))
                .andExpect(jsonPath("$[1].lessonTimeDTOResponse.lessonEnd", Matchers.is("2020-09-05T11:15:00")))
                .andExpect(jsonPath("$[1].groupDTOResponses.size()", Matchers.is(2)))
                .andExpect(jsonPath("$[1].groupDTOResponses.[0].groupId", Matchers.is(1)))
                .andExpect(jsonPath("$[1].groupDTOResponses.[0].groupName", Matchers.is("aa-01")))
                .andExpect(jsonPath("$[1].groupDTOResponses.[1].groupId", Matchers.is(2)))
                .andExpect(jsonPath("$[1].groupDTOResponses.[1].groupName", Matchers.is("aa-02")));
    }

    @Test
    public void findTimetableForProfessor_shouldThrowExceptionOnBadRequest() throws Exception {
        mockMvc.perform(get("/api/professorTimetablessss")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void findTimetableForProfessor_shouldThrowExceptionWhenRequestedWithInvalidParameters() throws Exception {
        mockMvc.perform(get("/api/professorTimetable/")
                .param("professorId", "fghfghfh")
                .param("startTime", "01.09.20 07:00")
                .param("endTime", "01.10.20 07:00"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void findTimetableForGroup_shouldRetrieve3LessonsFromDBForProfessor() throws Exception {
        mockMvc.perform(get("/api/groupTimetable/")
                .param("groupId", "1")
                .param("startTime", "01.09.20 07:00")
                .param("endTime", "01.10.21 07:00"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()", Matchers.is(3)))
                .andExpect(jsonPath("$[0].lessonId", Matchers.is(1)))
                .andExpect(jsonPath("$[0].professorDTOResponse.professorId", Matchers.is(1)))
                .andExpect(jsonPath("$[0].professorDTOResponse.firstName", Matchers.is("Gerbert")))
                .andExpect(jsonPath("$[0].professorDTOResponse.lastName", Matchers.is("Shildt")))
                .andExpect(jsonPath("$[0].courseDTOResponse.courseId", Matchers.is(1)))
                .andExpect(jsonPath("$[0].courseDTOResponse.courseName", Matchers.is("History")))
                .andExpect(jsonPath("$[0].courseDTOResponse.description", Matchers.is("History Description")))
                .andExpect(jsonPath("$[0].classRoomDTOResponse.roomId", Matchers.is(1)))
                .andExpect(jsonPath("$[0].classRoomDTOResponse.roomNumber", Matchers.is(101)))
                .andExpect(jsonPath("$[0].lessonTimeDTOResponse.timeId", Matchers.is(1)))
                .andExpect(jsonPath("$[0].lessonTimeDTOResponse.lessonStart", Matchers.is("2020-09-05T08:00:00")))
                .andExpect(jsonPath("$[0].lessonTimeDTOResponse.lessonEnd", Matchers.is("2020-09-05T09:30:00")))
                .andExpect(jsonPath("$[0].groupDTOResponses.size()", Matchers.is(2)))
                .andExpect(jsonPath("$[0].groupDTOResponses.[0].groupId", Matchers.is(1)))
                .andExpect(jsonPath("$[0].groupDTOResponses.[0].groupName", Matchers.is("aa-01")))
                .andExpect(jsonPath("$[0].groupDTOResponses.[1].groupId", Matchers.is(2)))
                .andExpect(jsonPath("$[0].groupDTOResponses.[1].groupName", Matchers.is("aa-02")))
                .andExpect(jsonPath("$[1].lessonId", Matchers.is(2)))
                .andExpect(jsonPath("$[1].professorDTOResponse.professorId", Matchers.is(2)))
                .andExpect(jsonPath("$[1].professorDTOResponse.firstName", Matchers.is("Albert")))
                .andExpect(jsonPath("$[1].professorDTOResponse.lastName", Matchers.is("Einshtein")))
                .andExpect(jsonPath("$[1].courseDTOResponse.courseId", Matchers.is(2)))
                .andExpect(jsonPath("$[1].courseDTOResponse.courseName", Matchers.is("Robotics")))
                .andExpect(jsonPath("$[1].courseDTOResponse.description", Matchers.is("Robotics Description")))
                .andExpect(jsonPath("$[1].classRoomDTOResponse.roomId", Matchers.is(2)))
                .andExpect(jsonPath("$[1].classRoomDTOResponse.roomNumber", Matchers.is(102)))
                .andExpect(jsonPath("$[1].lessonTimeDTOResponse.timeId", Matchers.is(1)))
                .andExpect(jsonPath("$[1].lessonTimeDTOResponse.lessonStart", Matchers.is("2020-09-05T08:00:00")))
                .andExpect(jsonPath("$[1].lessonTimeDTOResponse.lessonEnd", Matchers.is("2020-09-05T09:30:00")))
                .andExpect(jsonPath("$[1].groupDTOResponses.size()", Matchers.is(1)))
                .andExpect(jsonPath("$[1].groupDTOResponses.[0].groupId", Matchers.is(1)))
                .andExpect(jsonPath("$[1].groupDTOResponses.[0].groupName", Matchers.is("aa-01")))
                .andExpect(jsonPath("$[2].lessonId", Matchers.is(4)))
                .andExpect(jsonPath("$[2].professorDTOResponse.professorId", Matchers.is(3)))
                .andExpect(jsonPath("$[2].professorDTOResponse.firstName", Matchers.is("Robert")))
                .andExpect(jsonPath("$[2].professorDTOResponse.lastName", Matchers.is("Martin")))
                .andExpect(jsonPath("$[2].courseDTOResponse.courseId", Matchers.is(4)))
                .andExpect(jsonPath("$[2].courseDTOResponse.courseName", Matchers.is("Architecture")))
                .andExpect(jsonPath("$[2].courseDTOResponse.description", Matchers.is("Architecture Description")))
                .andExpect(jsonPath("$[2].classRoomDTOResponse.roomId", Matchers.is(3)))
                .andExpect(jsonPath("$[2].classRoomDTOResponse.roomNumber", Matchers.is(103)))
                .andExpect(jsonPath("$[2].lessonTimeDTOResponse.timeId", Matchers.is(2)))
                .andExpect(jsonPath("$[2].lessonTimeDTOResponse.lessonStart", Matchers.is("2020-09-05T09:45:00")))
                .andExpect(jsonPath("$[2].lessonTimeDTOResponse.lessonEnd", Matchers.is("2020-09-05T11:15:00")))
                .andExpect(jsonPath("$[2].groupDTOResponses.size()", Matchers.is(2)))
                .andExpect(jsonPath("$[2].groupDTOResponses.[0].groupId", Matchers.is(1)))
                .andExpect(jsonPath("$[2].groupDTOResponses.[0].groupName", Matchers.is("aa-01")))
                .andExpect(jsonPath("$[2].groupDTOResponses.[1].groupId", Matchers.is(2)))
                .andExpect(jsonPath("$[2].groupDTOResponses.[1].groupName", Matchers.is("aa-02")));
    }

    @Test
    public void findTimetableForGroup_shouldThrowExceptionOnBadRequest() throws Exception {
        mockMvc.perform(get("/api/groupTimetablessss")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void findTimetableForGroup_shouldThrowExceptionWhenRequestedWithInvalidParameters() throws Exception {
        mockMvc.perform(get("/api/groupTimetable/")
                .param("groupId", "hjghgjghjghjg")
                .param("startTime", "01.09.20 07:00")
                .param("endTime", "01.10.21 07:00"))
                .andExpect(status().is4xxClientError());
    }
}
