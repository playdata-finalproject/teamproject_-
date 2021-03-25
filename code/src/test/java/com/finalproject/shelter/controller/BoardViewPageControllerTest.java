package com.finalproject.shelter.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.finalproject.shelter.ShelterApplicationTests;
import com.finalproject.shelter.controller.page.board.BoardViewPageController;
import com.finalproject.shelter.model.entity.Board;
import com.finalproject.shelter.model.entity.Categorytable;
import com.finalproject.shelter.repository.AnswerRepositoryTest;
import com.finalproject.shelter.repository.BoardRepositoryTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.logging.Logger;

@AutoConfigureMockMvc
public class BoardViewPageControllerTest extends ShelterApplicationTests {

    @Autowired
    private BoardViewPageController boardViewPageController;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private static final Logger log = Logger.getLogger(BoardRepositoryTest.class.getName());


}
