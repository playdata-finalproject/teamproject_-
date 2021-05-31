package com.finalproject.shelter.account;

import com.finalproject.shelter.domain.model.entity.userDomain.Account;
import com.finalproject.shelter.domain.repository.AccountRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AccountRepository accountRepository;


    @DisplayName("회원 가입 연결 확인 테스트")
    @Test
    void signupForm() throws Exception {
        mockMvc.perform(get("/account/register"))
                .andExpect(status().isOk())
                .andExpect(view().name("account/register"))
                .andExpect(model().attributeExists("signUpForm"))
                .andExpect(unauthenticated());
    }

    @DisplayName("회원 가입 처리 binding 테스트(입력값 오류)")
    @Test
    void signUpSubmit_with_wrong_input() throws Exception {
        mockMvc.perform(post("/account/register")
                .param("username","moons")
                .param("nickname", "jihyeon")
                .param("email", "email..")
                .param("password", "1234")
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("account/register"))
                .andExpect(unauthenticated());
    }

    @DisplayName("회원 가입 처리 바인딩 테스트(입력값 정상)")
    @Test
    void signUpSubmit_with_correct_input() throws Exception {
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
//        assertNotNull(account.getEmailCheckToken());
        assertNotEquals(account.getPassword(), "12345678");
        assertTrue(accountRepository.existsByEmail("jihyeon@gmail.com"));
//        then(emailService).should().sendEmail(any(EmailMessage.class)); // 메일 전송 메서드가 실행되었는지 확인
    }

//    @DisplayName("인증 메일 확인 - 입력값 오류")
//    @Test
//    void checkEmailToken_with_wrong_input() throws Exception {
//        mockMvc.perform(get("/check-email-token")
//                .param("token", "sagdasga")
//                .param("email", "email@gmail.com"))
//                .andExpect(status().isOk())
//                .andExpect(model().attributeExists("error"))
//                .andExpect(view().name("account/checked-email"))
//                .andExpect(unauthenticated()); // security와 연동이 되기 때문에 이러한 테스트가 가능하다.
//    }

//    @DisplayName("인증 메일 확인 - 입력값 정상")
//    @Test
//    void checkEmailToken() throws Exception {
//        Account account = Account.builder()
//                .email("test@email.com")
//                .password("12345678")
//                .nickname("jihyeon")
//                .build();
//        Account newAccount = accountRepository.save(account);
//        newAccount.generateEmailCheckToken();
//
//        mockMvc.perform(get("/check-email-token")
//                .param("token", newAccount.getEmailCheckToken())
//                .param("email", newAccount.getEmail()))
//                .andExpect(status().isOk())
//                .andExpect(model().attributeDoesNotExist("error"))
//                .andExpect(model().attributeExists("nickname"))
//                .andExpect(model().attributeExists("numberOfUser"))
//                .andExpect(view().name("account/checked-email"))
//                .andExpect(authenticated().withUsername(account.getNickname()));
//    }
}
