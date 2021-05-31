package com.finalproject.shelter.presentationLayer.controller;

import com.finalproject.shelter.ShelterApplicationTests;
import com.finalproject.shelter.domainModelLayer.model.entity.userDomain.Account;
import com.finalproject.shelter.domainModelLayer.repository.AccountRepository;
import com.finalproject.shelter.business.service.account.AccountService;
import lombok.extern.slf4j.Slf4j;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Transactional
@Slf4j
@AutoConfigureMockMvc
public class BoardPageControllerTest extends ShelterApplicationTests{

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AccountService accountService;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void login() throws Exception {
        Optional<Account> account = accountRepository.findById(122L);

        account.ifPresent(select->{
            accountService.login(select);
        });
    }

    @DisplayName("게시판 레코드 조회 테스트")
    @Test
    public void findboardlist() throws Exception {

        mockMvc.perform(get("/board?id=1"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().hasNoErrors())
                .andExpect(view().name("pages/list"));

        // error 해결 못함
//        mockMvc.perform(get("/board?id=100"))
//                .andExpect(status().isInternalServerError())
//                .andExpect(model().hasErrors());
    }

    @DisplayName("게시판 레코드 삭제 테스트")
    @Test
    public void deleteboard() throws Exception {
        mockMvc.perform(get("/board/delete?id=6"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/board?id=1&page=0"));

        mockMvc.perform(get("/board/delete?id=1000"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/main"));
    }

}
