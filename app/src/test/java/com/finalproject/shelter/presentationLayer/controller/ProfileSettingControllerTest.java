package com.finalproject.shelter.presentationLayer.controller;

import com.finalproject.shelter.ShelterApplicationTests;
import com.finalproject.shelter.domain.model.entity.userDomain.Account;
import com.finalproject.shelter.domain.repository.AccountRepository;
import com.finalproject.shelter.business.service.account.AccountService;
import com.finalproject.shelter.business.settings.form.identity.IdentityForm;
import com.finalproject.shelter.business.settings.form.password.PasswordForm;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@Transactional
@SpringBootTest
@Slf4j
public class ProfileSettingControllerTest extends ShelterApplicationTests {

    public static final String SETTINGS_PASSWORD_VIEW_NAME = "account/password";
    public static final String SETTINGS_PASSWORD_URL = "/settings/password";
    public static final String SETTINGS_ACCOUNT_VIEW_NAME = "account/account";
    public static final String SETTINGS_ACCOUNT_URL = "/settings/account";
    public static final String SETTING_DELETE_URL = "/settings/delete";
    public static final String SETTING_DELETE_VIEW_NAME = "account/delete";
    public static final String REDIRECT_HOME = "redirect:"+"/main";
    public static final String HOME = "/main";

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AccountService accountService;

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

    @DisplayName("Password 변경 페이지 test")
    @Test
    public void updatePasswordForm() throws Exception {
        mockMvc.perform(get(SETTINGS_PASSWORD_URL))
                .andExpect(model().attribute("passwordForm",new PasswordForm()))
                .andExpect(view().name(SETTINGS_PASSWORD_VIEW_NAME));
    }

    @DisplayName("Password 변경 후 message 확인 테스트")
    @Test
    public void updatePassword() throws Exception {
        mockMvc.perform(post(SETTINGS_PASSWORD_URL)
                .param("newPassword","12345678")
                .param("newPasswordConfirm","12345678")
                .with(csrf()))
                .andExpect(flash().attribute("message","패스워드를 변경하였습니다."))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name(REDIRECT_HOME));
    }

    @DisplayName("닉네임 변경 페이지 test")
    @Test
    public void updateAccountForm(){

        Optional<Account> account = accountRepository.findById(122L);

        account.ifPresent(select->{
            try {
                mockMvc.perform(get(SETTINGS_ACCOUNT_URL))
                        .andExpect(model().attribute("identityForm",new IdentityForm(select.getIdentity())))
                        .andExpect(view().name(SETTINGS_ACCOUNT_VIEW_NAME));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @DisplayName("닉네임 변경 후 message 확인 테스트")
    @Test
    public void updateAccount() throws Exception {
        mockMvc.perform(post(SETTINGS_ACCOUNT_URL)
                .param("identity","모모시")
                .with(csrf()))
                .andExpect(flash().attribute("message","닉네임을 수정했습니다."))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name(REDIRECT_HOME));

        Optional<Account> account = accountRepository.findById(122L);

        account.ifPresent(select->{
            assertThat(select.getIdentity()).isEqualTo("모모시");
        });
    }

}
