package com.finalproject.shelter.controller.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.finalproject.shelter.ShelterApplicationTests;
import com.finalproject.shelter.model.Header;
import com.finalproject.shelter.model.entity.AdminUser;
import com.finalproject.shelter.model.network.request.AdminUserApiRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@AutoConfigureMockMvc
public class AdminUserApiControllerTest extends ShelterApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void getTest() throws Exception{

        ResultActions result = mockMvc.perform(get("/api/adminuser/3"))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void PostTest() throws Exception{

        AdminUserApiRequest adminUserApiRequest = AdminUserApiRequest.builder()
                .userId("오이오이")
                .password("34324")
                .name("김두식")
                .build();

        String content = objectMapper.writeValueAsString(Header.OK(adminUserApiRequest));

        mockMvc.perform(post("/api/adminuser")
                        .content(content)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void PutTest() throws Exception{

        AdminUserApiRequest adminUserApiRequest = AdminUserApiRequest.builder()
                .id(9L)
                .userId("오이오이")
                .password("1231234")
                .name("김두식")
                .build();

        String content = objectMapper.writeValueAsString(Header.OK(adminUserApiRequest));

        ResultActions result = mockMvc.perform(put("/api/adminuser")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }
}
