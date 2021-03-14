package com.finalproject.shelter.controller.xControllerTest;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.finalproject.shelter.ShelterApplicationTests;
import com.finalproject.shelter.controller.page.board.BoardPageController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@AutoConfigureMockMvc
public class BoardApiControllerTest extends ShelterApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private BoardPageController boardPageController;

    @Test
    public void getTest() throws Exception{
        ResultActions result = mockMvc.perform(get("/board?id=3&page=1"))
                .andDo(MockMvcResultHandlers.print());
    }
}
