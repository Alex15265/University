package com.foxminded.university.controllers.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.foxminded.university.dto.group.GroupDTORequest;
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
public class GroupsRestControllerSystemTest {
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
    public void showAllGroups_shouldRetrieveAllGroupsFromDB() throws Exception {
        mockMvc.perform(get("/api/groups/"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.size()", is(4)))
                .andExpect(jsonPath("$[0].groupId", is(1)))
                .andExpect(jsonPath("$[0].groupName", is("aa-01")))
                .andExpect(jsonPath("$[1].groupId", is(2)))
                .andExpect(jsonPath("$[1].groupName", is("aa-02")))
                .andExpect(jsonPath("$[2].groupId", is(3)))
                .andExpect(jsonPath("$[2].groupName", is("aa-03")))
                .andExpect(jsonPath("$[3].groupId", is(4)))
                .andExpect(jsonPath("$[3].groupName", is("aa-04")));
    }

    @Test
    public void showAllGroups_shouldThrowExceptionOnBadRequest() throws Exception {
        mockMvc.perform(get("/api/gr")
                .contentType(APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void showGroupByID_shouldRetrieveGroupFromDBByID() throws Exception {
        mockMvc.perform(get("/api/groups/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.groupId", is(1)))
                .andExpect(jsonPath("$.groupName", is("aa-01")));
    }

    @Test
    public void showGroupByID_shouldThrowException() throws Exception {
        mockMvc.perform(get("/api/groups/{group_id}", "bad_request")
                .contentType(APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void showStudentsByGroup_shouldRetrieveStudentsByGroupFromDB() throws Exception {
        mockMvc.perform(get("/api/groups/2/students"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.size()", is(2)))
                .andExpect(jsonPath("$[0].studentId", is(2)))
                .andExpect(jsonPath("$[0].firstName", is("Ann")))
                .andExpect(jsonPath("$[0].lastName", is("White")))
                .andExpect(jsonPath("$[0].groupDTOResponse.groupId", is(2)))
                .andExpect(jsonPath("$[0].groupDTOResponse.groupName", is("aa-02")))
                .andExpect(jsonPath("$[1].studentId", is(3)))
                .andExpect(jsonPath("$[1].firstName", is("Leo")))
                .andExpect(jsonPath("$[1].lastName", is("Messi")))
                .andExpect(jsonPath("$[1].groupDTOResponse.groupId", is(2)))
                .andExpect(jsonPath("$[1].groupDTOResponse.groupName", is("aa-02")));
    }

    @Test
    public void saveGroup_shouldAddNewGroupToDBAndReturnIt() throws Exception {
        GroupDTORequest groupDTORequest = new GroupDTORequest();
        groupDTORequest.setGroupName("New Group");

        mockMvc.perform(post("/api/groups/")
                .content(objectMapper.writeValueAsString(groupDTORequest))
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.groupId", is(5)))
                .andExpect(jsonPath("$.groupName", is("New Group")));
    }

    @Test
    public void saveGroup_shouldThrowExceptionWhenRequestedWithInvalidParameters() throws Exception {
        GroupDTORequest groupDTORequest = new GroupDTORequest();
        groupDTORequest.setGroupName("");

        mockMvc.perform(post("/api/groups/")
                .content(objectMapper.writeValueAsString(groupDTORequest))
                .contentType(APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void updateGroup_shouldUpdateGroupByIDInDBAndReturnIt() throws Exception {
        GroupDTORequest groupDTORequest = new GroupDTORequest();
        groupDTORequest.setGroupName("New Group");

        mockMvc.perform(patch("/api/groups/4")
                .content(objectMapper.writeValueAsString(groupDTORequest))
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.groupId", is(4)))
                .andExpect(jsonPath("$.groupName", is("New Group")));
    }

    @Test
    public void updateGroup_shouldThrowExceptionWhenRequestedWithInvalidParameters() throws Exception {
        GroupDTORequest groupDTORequest = new GroupDTORequest();
        groupDTORequest.setGroupName("");

        mockMvc.perform(patch("/api/groups/")
                .content(objectMapper.writeValueAsString(groupDTORequest))
                .contentType(APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void deleteGroup_shouldDeleteGroupFromDB() throws Exception {
        mockMvc.perform(delete("/api/groups/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteGroup_shouldThrowExceptionOnBadRequest() throws Exception {
        mockMvc.perform(delete("/api/groups/groupID"))
                .andExpect(status().isBadRequest());
    }
}
