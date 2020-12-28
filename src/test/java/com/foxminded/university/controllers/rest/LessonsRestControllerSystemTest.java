package com.foxminded.university.controllers.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.foxminded.university.dto.lesson.LessonDTORequest;
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
@Sql(value = "/create_tables_for_courses.sql",
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = "/create_tables_for_lessontimes.sql",
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = "/create_tables_for_classrooms.sql",
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = "/create_tables_for_lessons.sql",
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class LessonsRestControllerSystemTest {
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
    public void showAllLessons_shouldRetrieveAllLessonsFromDB() throws Exception {
        mockMvc.perform(get("/api/lessons/"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()", Matchers.is(4)))
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
                .andExpect(jsonPath("$[2].lessonId", Matchers.is(3)))
                .andExpect(jsonPath("$[2].professorDTOResponse.professorId", Matchers.is(3)))
                .andExpect(jsonPath("$[2].professorDTOResponse.firstName", Matchers.is("Robert")))
                .andExpect(jsonPath("$[2].professorDTOResponse.lastName", Matchers.is("Martin")))
                .andExpect(jsonPath("$[2].courseDTOResponse.courseId", Matchers.is(3)))
                .andExpect(jsonPath("$[2].courseDTOResponse.courseName", Matchers.is("Criminology")))
                .andExpect(jsonPath("$[2].courseDTOResponse.description", Matchers.is("Criminology Description")))
                .andExpect(jsonPath("$[2].classRoomDTOResponse.roomId", Matchers.is(3)))
                .andExpect(jsonPath("$[2].classRoomDTOResponse.roomNumber", Matchers.is(103)))
                .andExpect(jsonPath("$[2].lessonTimeDTOResponse.timeId", Matchers.is(1)))
                .andExpect(jsonPath("$[2].lessonTimeDTOResponse.lessonStart", Matchers.is("2020-09-05T08:00:00")))
                .andExpect(jsonPath("$[2].lessonTimeDTOResponse.lessonEnd", Matchers.is("2020-09-05T09:30:00")))
                .andExpect(jsonPath("$[2].groupDTOResponses.size()", Matchers.is(1)))
                .andExpect(jsonPath("$[2].groupDTOResponses.[0].groupId", Matchers.is(2)))
                .andExpect(jsonPath("$[2].groupDTOResponses.[0].groupName", Matchers.is("aa-02")))
                .andExpect(jsonPath("$[3].lessonId", Matchers.is(4)))
                .andExpect(jsonPath("$[3].professorDTOResponse.professorId", Matchers.is(3)))
                .andExpect(jsonPath("$[3].professorDTOResponse.firstName", Matchers.is("Robert")))
                .andExpect(jsonPath("$[3].professorDTOResponse.lastName", Matchers.is("Martin")))
                .andExpect(jsonPath("$[3].courseDTOResponse.courseId", Matchers.is(4)))
                .andExpect(jsonPath("$[3].courseDTOResponse.courseName", Matchers.is("Architecture")))
                .andExpect(jsonPath("$[3].courseDTOResponse.description", Matchers.is("Architecture Description")))
                .andExpect(jsonPath("$[3].classRoomDTOResponse.roomId", Matchers.is(3)))
                .andExpect(jsonPath("$[3].classRoomDTOResponse.roomNumber", Matchers.is(103)))
                .andExpect(jsonPath("$[3].lessonTimeDTOResponse.timeId", Matchers.is(2)))
                .andExpect(jsonPath("$[3].lessonTimeDTOResponse.lessonStart", Matchers.is("2020-09-05T09:45:00")))
                .andExpect(jsonPath("$[3].lessonTimeDTOResponse.lessonEnd", Matchers.is("2020-09-05T11:15:00")))
                .andExpect(jsonPath("$[3].groupDTOResponses.size()", Matchers.is(2)))
                .andExpect(jsonPath("$[3].groupDTOResponses.[0].groupId", Matchers.is(1)))
                .andExpect(jsonPath("$[3].groupDTOResponses.[0].groupName", Matchers.is("aa-01")))
                .andExpect(jsonPath("$[3].groupDTOResponses.[1].groupId", Matchers.is(2)))
                .andExpect(jsonPath("$[3].groupDTOResponses.[1].groupName", Matchers.is("aa-02")));
    }

    @Test
    public void showAllLessons_shouldThrowExceptionOnBadRequest() throws Exception {
        mockMvc.perform(get("/api/lesson")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void showLessonByID_shouldRetrieveLessonFromDBByID() throws Exception {
        mockMvc.perform(get("/api/lessons/4"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.lessonId", Matchers.is(4)))
                .andExpect(jsonPath("$.professorDTOResponse.professorId", Matchers.is(3)))
                .andExpect(jsonPath("$.professorDTOResponse.firstName", Matchers.is("Robert")))
                .andExpect(jsonPath("$.professorDTOResponse.lastName", Matchers.is("Martin")))
                .andExpect(jsonPath("$.courseDTOResponse.courseId", Matchers.is(4)))
                .andExpect(jsonPath("$.courseDTOResponse.courseName", Matchers.is("Architecture")))
                .andExpect(jsonPath("$.courseDTOResponse.description", Matchers.is("Architecture Description")))
                .andExpect(jsonPath("$.classRoomDTOResponse.roomId", Matchers.is(3)))
                .andExpect(jsonPath("$.classRoomDTOResponse.roomNumber", Matchers.is(103)))
                .andExpect(jsonPath("$.lessonTimeDTOResponse.timeId", Matchers.is(2)))
                .andExpect(jsonPath("$.lessonTimeDTOResponse.lessonStart", Matchers.is("2020-09-05T09:45:00")))
                .andExpect(jsonPath("$.lessonTimeDTOResponse.lessonEnd", Matchers.is("2020-09-05T11:15:00")))
                .andExpect(jsonPath("$.groupDTOResponses.size()", Matchers.is(2)))
                .andExpect(jsonPath("$.groupDTOResponses.[0].groupId", Matchers.is(1)))
                .andExpect(jsonPath("$.groupDTOResponses.[0].groupName", Matchers.is("aa-01")))
                .andExpect(jsonPath("$.groupDTOResponses.[1].groupId", Matchers.is(2)))
                .andExpect(jsonPath("$.groupDTOResponses.[1].groupName", Matchers.is("aa-02")));
    }

    @Test
    public void showLessonByID_shouldThrowException() throws Exception {
        mockMvc.perform(get("/api/lessons/{lesson_id}", "bad_request")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void saveLesson_shouldAddNewLessonToDBAndReturnIt() throws Exception {
        LessonDTORequest lessonDTORequest = new LessonDTORequest();
        lessonDTORequest.setCourseId(1);
        lessonDTORequest.setRoomId(1);
        lessonDTORequest.setProfessorId(1);
        lessonDTORequest.setTimeId(4);

        mockMvc.perform(post("/api/lessons/")
                .content(objectMapper.writeValueAsString(lessonDTORequest))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.lessonId", Matchers.is(5)))
                .andExpect(jsonPath("$.professorDTOResponse.professorId", Matchers.is(1)))
                .andExpect(jsonPath("$.professorDTOResponse.firstName", Matchers.is("Gerbert")))
                .andExpect(jsonPath("$.professorDTOResponse.lastName", Matchers.is("Shildt")))
                .andExpect(jsonPath("$.courseDTOResponse.courseId", Matchers.is(1)))
                .andExpect(jsonPath("$.courseDTOResponse.courseName", Matchers.is("History")))
                .andExpect(jsonPath("$.courseDTOResponse.description", Matchers.is("History Description")))
                .andExpect(jsonPath("$.classRoomDTOResponse.roomId", Matchers.is(1)))
                .andExpect(jsonPath("$.classRoomDTOResponse.roomNumber", Matchers.is(101)))
                .andExpect(jsonPath("$.lessonTimeDTOResponse.timeId", Matchers.is(4)))
                .andExpect(jsonPath("$.lessonTimeDTOResponse.lessonStart", Matchers.is("2020-09-14T11:30:00")))
                .andExpect(jsonPath("$.lessonTimeDTOResponse.lessonEnd", Matchers.is("2020-09-14T13:00:00")));
    }

    @Test
    public void saveLesson_shouldThrowExceptionWhenRequestedWithInvalidParameters() throws Exception {
        LessonDTORequest lessonDTORequest = new LessonDTORequest();
        lessonDTORequest.setCourseId(0);
        lessonDTORequest.setRoomId(-2);
        lessonDTORequest.setProfessorId(0);
        lessonDTORequest.setTimeId(-5);

        mockMvc.perform(post("/api/lessons/")
                .content(objectMapper.writeValueAsString(lessonDTORequest))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void updateLesson_shouldUpdateLessonByIDInDBAndReturnIt() throws Exception {
        LessonDTORequest lessonDTORequest = new LessonDTORequest();
        lessonDTORequest.setCourseId(1);
        lessonDTORequest.setRoomId(1);
        lessonDTORequest.setProfessorId(1);
        lessonDTORequest.setTimeId(4);

        mockMvc.perform(patch("/api/lessons/2")
                .content(objectMapper.writeValueAsString(lessonDTORequest))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.lessonId", Matchers.is(2)))
                .andExpect(jsonPath("$.professorDTOResponse.professorId", Matchers.is(1)))
                .andExpect(jsonPath("$.professorDTOResponse.firstName", Matchers.is("Gerbert")))
                .andExpect(jsonPath("$.professorDTOResponse.lastName", Matchers.is("Shildt")))
                .andExpect(jsonPath("$.courseDTOResponse.courseId", Matchers.is(1)))
                .andExpect(jsonPath("$.courseDTOResponse.courseName", Matchers.is("History")))
                .andExpect(jsonPath("$.courseDTOResponse.description", Matchers.is("History Description")))
                .andExpect(jsonPath("$.classRoomDTOResponse.roomId", Matchers.is(1)))
                .andExpect(jsonPath("$.classRoomDTOResponse.roomNumber", Matchers.is(101)))
                .andExpect(jsonPath("$.lessonTimeDTOResponse.timeId", Matchers.is(4)))
                .andExpect(jsonPath("$.lessonTimeDTOResponse.lessonStart", Matchers.is("2020-09-14T11:30:00")))
                .andExpect(jsonPath("$.lessonTimeDTOResponse.lessonEnd", Matchers.is("2020-09-14T13:00:00")));
    }

    @Test
    public void updateLesson_shouldThrowExceptionWhenRequestedWithInvalidParameters() throws Exception {
        LessonDTORequest lessonDTORequest = new LessonDTORequest();
        lessonDTORequest.setCourseId(0);
        lessonDTORequest.setRoomId(-2);
        lessonDTORequest.setProfessorId(0);
        lessonDTORequest.setTimeId(-5);

        mockMvc.perform(patch("/api/lessons/")
                .content(objectMapper.writeValueAsString(lessonDTORequest))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void deleteLessons_shouldDeleteLessonFromDB() throws Exception {
        mockMvc.perform(delete("/api/lessons/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteLesson_shouldThrowExceptionOnBadRequest() throws Exception {
        mockMvc.perform(delete("/api/lessons/lessonID"))
                .andExpect(status().isBadRequest());
    }
}