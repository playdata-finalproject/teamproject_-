package com.finalproject.shelter.presentationLayer.controller;

import com.finalproject.shelter.ShelterApplicationTests;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@Transactional
@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
public class ProfileControllerTest extends ShelterApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("profile 아이디 정보 검색 테스트")
    public void viewProfile() throws Exception {

        mockMvc.perform(get("/profile/namename"))
                .andExpect(model().attribute("isOwner",1))
                .andExpect(status().isOk())
                .andExpect(view().name("account/profile"));

    }

}
