package com.finalproject.shelter.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.finalproject.shelter.ShelterApplicationTests;
import com.finalproject.shelter.controller.page.board.BoardFormPageController;
import com.finalproject.shelter.model.entity.Board;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
public class BoardFormPageControllerTest extends ShelterApplicationTests {

    @Autowired
    private BoardFormPageController boardFormPageController;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Before("")
    public void setUp() throws Exception{
        mockMvc = MockMvcBuilders.standaloneSetup(boardFormPageController).build();
    }

    @Test
    @Transactional
    public void postTest () throws Exception{
        Board board = Board.builder()
                .title("")
                .contents("")
                .build();

        String content = objectMapper.writeValueAsString(board);

        mockMvc.perform(post("/board/form").content(content))
                .andExpect(status().isOk())
                .andDo(print());
    }
}
