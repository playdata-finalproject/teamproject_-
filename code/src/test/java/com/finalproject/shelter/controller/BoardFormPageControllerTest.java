package com.finalproject.shelter.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.finalproject.shelter.ShelterApplicationTests;
import com.finalproject.shelter.model.entity.Account;
import com.finalproject.shelter.model.entity.Board;
import com.finalproject.shelter.repository.AccountRepository;
import com.finalproject.shelter.repository.CategoryRepository;
import com.finalproject.shelter.service.AccountService;
import com.finalproject.shelter.service.Logic.BoardLogicService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Transactional
@AutoConfigureMockMvc
@WebAppConfiguration
@Slf4j
public class BoardFormPageControllerTest extends ShelterApplicationTests {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private BoardLogicService boardLogicService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void login() throws Exception {
        log.info("before : 로그인 시작");
        Optional<Account> account = accountRepository.findById(122L);

        account.ifPresent(select->{
            accountService.login(select);
        });
    }

    @DisplayName("form 작성 및 수정 Controller 테스트")
    @Test
    public void writeview() throws Exception {

        Board board = boardLogicService.readCategory("1");
        Board board1 = boardLogicService.newuserboard(board,accountRepository);

        mockMvc.perform(get("/board/form?name=write&categoryid=1"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("pages/form"))
                .andExpect(model().attribute("board",board1))
                .andExpect(model().attribute("eachboard",board));

        mockMvc.perform(get("/board/form?boardid=6&name=modify&categoryid=1"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("pages/form"))
                .andExpect(model().attribute("eachboard",board))
                .andExpect(model().attribute("board",board));

        mockMvc.perform(get("/board/form?boardid=6&name=sfsefag&categoryid=1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/main"));

    }


    @DisplayName("")
    @Test
    public void postform() throws Exception {

        ObjectMapper objectMapper = new ObjectMapper();

        Board board = Board.builder()
                .title("TitleTest")
                .nickname("asdfw")
                .user(accountRepository.getOne(76L))
                .category(categoryRepository.getOne(1L))
                .contents("lkfjseisjflesselkfjliagjoi4ewghwoigilasdgj")
                .build();

        String content = objectMapper.writeValueAsString(board);



    }
}
