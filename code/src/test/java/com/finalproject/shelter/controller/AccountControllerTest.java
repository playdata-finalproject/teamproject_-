package com.finalproject.shelter.controller;

import com.finalproject.shelter.ShelterApplicationTests;
import com.finalproject.shelter.model.entity.Account;
import com.finalproject.shelter.repository.AccountRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class AccountControllerTest extends ShelterApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AccountRepository accountRepository;

    @DisplayName("회원 가입 처리 binding 테스트")
    @Test
    void register() throws Exception {
        mockMvc.perform(post("/account/register")
                .param("username","moons")
                .param("nickname", "jihyeon")
                .param("email", "email..")
                .param("password", "1234")
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("account/register"))
                .andExpect(unauthenticated());

        mockMvc.perform(post("/account/register")
                .param("username", "jihyeon")
                .param("identity", "identities")
                .param("email", "jihyeon@gmail.com")
                .param("password", "12345678")
                .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(authenticated().withUsername("jihyeon"));

        Account account = accountRepository.findByEmail("jihyeon@gmail.com");
        assertNotNull(account);
        assertNotEquals(account.getPassword(), "12345678");
        assertTrue(accountRepository.existsByEmail("jihyeon@gmail.com"));
    }

    @DisplayName("profile 연결 테스트")
    @Test
    public void viewprofile() throws Exception {
        Account account = accountRepository.findByIdentity("asdf");

        mockMvc.perform(get("/account/profile/asdf"))
                .andExpect(view().name("account/profile"))
                .andExpect(model().attribute("isOwner",account))
                .andExpect(model().hasNoErrors())
                .andExpect(status().is3xxRedirection());
    }

}
