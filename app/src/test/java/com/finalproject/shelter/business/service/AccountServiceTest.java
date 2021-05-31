package com.finalproject.shelter.business.service;

import com.finalproject.shelter.ShelterApplicationTests;
import com.finalproject.shelter.domain.model.entity.userDomain.Account;
import com.finalproject.shelter.domain.repository.AccountRepository;
import com.finalproject.shelter.business.service.account.AccountService;
import com.finalproject.shelter.business.settings.form.password.PasswordForm;
import com.finalproject.shelter.business.settings.form.signup.SignUpForm;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@Slf4j
public class AccountServiceTest extends ShelterApplicationTests {

    @Autowired
    private AccountService accountService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AccountRepository accountRepository;

    @Test
    public void processNewAccount(){
    }

    @DisplayName("Account 칼럼 저장 테스트")
    @Test
    public void saveNewAccount(){
        SignUpForm signUpForm = new SignUpForm();

        signUpForm.setUsername("test567");
        signUpForm.setIdentity("테스트닉네임");
        signUpForm.setEmail("ejkflsf@sjklfes.com");
        signUpForm.setPassword("45674567");

        Account account = accountService.saveNewAccount(signUpForm);

        assertThat(account.getUsername()).isEqualTo("test567");
        assertThat(account.getIdentity()).isEqualTo("테스트닉네임");
        assertThat(account.getEmail()).isEqualTo("ejkflsf@sjklfes.com");
        assertThat(account.getEnabled()).isEqualTo(true);
        Assertions.assertTrue(passwordEncoder.matches("45674567",account.getPassword()));
    }

    @Test
    public void sendsignUpConfirmEmail(){

    }

    @DisplayName("로그인 테스트")
    @Test
    public void login(){
        Optional<Account> account = accountRepository.findById(122L);

        account.ifPresent(select->{
            accountService.login(select);
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            assertThat(authentication.getName()).isEqualTo(select.getUsername());
        });

        Account account1 = Account.builder()
                .username("aaa")
                .password("1234")
                .nickname("werwr")
                .email("wefsdf@sdjafklsef")
                .build();
        accountService.login(account1);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        assertThat(authentication.getName()).isEqualTo(account1.getUsername());

    }

    @DisplayName("유저 정보 확인 테스트")
    @Test
    public void loadUserByUsername(){
        UserDetails userDetail1 = accountService.loadUserByUsername("test12");
        assertThat(userDetail1.getUsername()).isEqualTo("test12");
    }


    @DisplayName("user 비밀번호 수정 테스트")
    @Test
    public void updatePassword(){
        Optional<Account> account = accountRepository.findById(96L);
        PasswordForm passwordForm = new PasswordForm();
        passwordForm.setNewPassword("abc1234");
        passwordForm.setNewPasswordConfirm("abc1234");

        account.ifPresent(select->{
            accountService.updatePassword(select,passwordForm);
        });

        Optional<Account> newaccount = accountRepository.findById(96L);
        newaccount.ifPresent(select->{
            Assertions.assertTrue(passwordEncoder.matches(passwordForm.getNewPassword(),select.getPassword()));
        });
    }

    @DisplayName("유저 ID 수정 확인 테스트")
    @Test
    public void updateIdentity(){

        Optional<Account> account = accountRepository.findById(96L);
        account.ifPresent(select->{
            accountService.updateIdentity(select,"test12345");
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            assertThat(authentication.getName()).isEqualTo(select.getUsername());
        });

        Optional<Account> account1 = accountRepository.findById(96L);
        account1.ifPresent(select->{
            assertThat(select.getIdentity()).isEqualTo("test12345");
        });
    }

}
