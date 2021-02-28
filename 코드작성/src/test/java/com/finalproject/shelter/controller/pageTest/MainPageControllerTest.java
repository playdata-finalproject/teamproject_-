package com.finalproject.shelter.controller.pageTest;


import com.finalproject.shelter.ShelterApplicationTests;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@AutoConfigureMockMvc
public class MainPageControllerTest extends ShelterApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void mainpage() throws Exception{
        check("/main","/pages/index");
    }

    private ResultActions check(String url, String location) throws Exception{
        ResultActions result = mockMvc.perform(get(url))
                .andExpect(status().isOk())
                .andExpect(view().name(location))
                .andDo(print());

        return result;
    }

}