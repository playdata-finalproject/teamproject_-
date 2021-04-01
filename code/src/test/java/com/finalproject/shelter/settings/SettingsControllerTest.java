package com.finalproject.shelter.settings;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.finalproject.shelter.WithAccount;
import com.finalproject.shelter.controller.page.ProfileSettingController;
import com.finalproject.shelter.model.entity.Account;
import com.finalproject.shelter.repository.AccountRepository;
import com.finalproject.shelter.service.AccountService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
class SettingsControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    AccountService accountService;

    @AfterEach
    void afterAll() {
        accountRepository.deleteAll();
    }


    @WithAccount("jihyeon")
    @DisplayName("패스워드 수정폼 ")
    @Test
    void updatePasswordForm() throws Exception {
        mockMvc.perform(get(ProfileSettingController.SETTINGS_PASSWORD_URL))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("account"))
                .andExpect(model().attributeExists("passwordForm"));
    }

    @WithAccount("jihyeon")
    @DisplayName("패스워드 수정하기 - 입력값 정상")
    @Test
    void updatePassword() throws Exception {
        String newPassword = "12345678";
        String newPasswordConfirm = "12345678";
        mockMvc.perform(post(ProfileSettingController.SETTINGS_PASSWORD_URL)
                .param("newPassword", newPassword)
                .param("newPasswordConfirm", newPasswordConfirm)
                .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl(ProfileSettingController.HOME))
                .andExpect(flash().attributeExists("message"));
        Account account = accountRepository.findByUsername("jihyeon");
        assertTrue(passwordEncoder.matches(newPassword, account.getPassword()));
    }

    @WithAccount("jihyeon")
    @DisplayName("패스워드 수정하기 - 패스워드 불일치")
    @Test
    void updatePasswordFail() throws Exception {
        String newPassword = "11111111";
        String newPasswordConfirm = "11111112";
        mockMvc.perform(post(ProfileSettingController.SETTINGS_PASSWORD_URL)
                .param("newPassword", newPassword)
                .param("newPasswordConfirm", newPasswordConfirm)
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name(ProfileSettingController.SETTINGS_PASSWORD_VIEW_NAME))
                .andExpect(model().attributeExists("account"))
                .andExpect(model().hasErrors());
    }

    @WithAccount("jihyeon")
    @DisplayName("닉네임 수정폼 ")
    @Test
    void updateAccountForm() throws Exception {
        mockMvc.perform(get(ProfileSettingController.SETTINGS_ACCOUNT_URL))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("account"))
                .andExpect(model().attributeExists("identityForm"));
    }

    @WithAccount("jihyeon")
    @DisplayName("닉네임 수정하기 - 입력값 정상")
    @Test
    void updateAccount() throws Exception {
        String identity = "abcdef";
        mockMvc.perform(post(ProfileSettingController.SETTINGS_ACCOUNT_URL)
                .param("identity", identity)
                .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl(ProfileSettingController.HOME))
                .andExpect(flash().attributeExists("message"));
        Account account = accountRepository.findByUsername("jihyeon");
    }

}
