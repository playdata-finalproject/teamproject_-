package com.finalproject.shelter.account;

import com.finalproject.shelter.domain.repository.AccountRepository;
import com.finalproject.shelter.business.service.account.AccountService;
import com.finalproject.shelter.business.settings.form.signup.SignUpForm;
import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@SpringBootTest
public class loginTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private AccountService accountService;
    @Autowired
    private AccountRepository accountRepository;

    @Test
    public void loginpage_test() throws Exception {
        mockMvc.perform(get("/account/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"))
                .andExpect(unauthenticated());
    }

    @Before
    public void before() {
        SignUpForm signUpForm = new SignUpForm();
        signUpForm.setUsername("jihyeons");
        signUpForm.setEmail("jihyeon@email.com");
        signUpForm.setIdentity("anotherking");
        signUpForm.setPassword("123123123");
        accountService.processNewAccount(signUpForm);
    }

    @After
    public void after() {
        accountRepository.deleteAll();
    }

    @Test
    @DisplayName("로그인 테스트")
    public void login_test() throws Exception {
        mockMvc.perform(post("/account/login")
                .param("username","jihyeons")
                .param("password","123123123")
                .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"))
                .andExpect(authenticated().withUsername("devkis"));
    }

    @Test
    @DisplayName("로그인 실패 테스트")
    public void login_fail_test() throws Exception {
        mockMvc.perform((post("/account/login"))
                .param("username", "jihyeons")
                .param("password", "123123124")
                .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login?error"))
                .andExpect(unauthenticated());
    }

    @Test
    @DisplayName("로그아웃 테스트")
    public void logout_test() throws Exception {
        mockMvc.perform(post("/logout").with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/main"))
                .andExpect(unauthenticated());
    }
}