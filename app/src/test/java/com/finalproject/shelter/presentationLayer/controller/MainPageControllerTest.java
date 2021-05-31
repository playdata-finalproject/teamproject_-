package com.finalproject.shelter.presentationLayer.controller;

import com.finalproject.shelter.ShelterApplicationTests;
import com.finalproject.shelter.domain.model.entity.userDomain.Account;
import com.finalproject.shelter.domain.repository.AccountRepository;
import com.finalproject.shelter.business.service.account.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@Slf4j
public class MainPageControllerTest extends ShelterApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AccountService accountService;

    @DisplayName("main page 출력 확인")
    @Test
    public void home() throws Exception {

        mockMvc.perform(get("/main"))
                .andExpect(status().isOk())
                .andExpect(view().name("pages/index"))
                .andExpect(unauthenticated());

        log.info("before : 로그인 시작");
        Optional<Account> account = accountRepository.findById(84L);
        account.ifPresent(select->{
            accountService.login(select);
        });

        mockMvc.perform(get("/main"))
                .andExpect(status().isOk())
                .andExpect(model().hasNoErrors())
                .andExpect(view().name("pages/index"))
                .andExpect(unauthenticated());
    }
}
