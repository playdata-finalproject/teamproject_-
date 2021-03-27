package com.finalproject.shelter.service;

import com.finalproject.shelter.ShelterApplicationTests;
import com.finalproject.shelter.model.entity.Account;
import com.finalproject.shelter.repository.AccountRepository;
import com.finalproject.shelter.settings.form.SignUpForm;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@Slf4j
public class AccountServiceTest extends ShelterApplicationTests {

    @Autowired
    private AccountService accountService;

    @Autowired
    private Validator validatorInjected;

    @Test
    public void processNewAccount(){
        SignUpForm signUpForm = new SignUpForm();

        signUpForm.setUsername("test567");
        signUpForm.setIdentity("테스트닉네임");
        signUpForm.setEmail("ejkflsf@sjklfes.com");
        signUpForm.setPassword("45674567");

        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        Validator validator = validatorFactory.getValidator();


        Set<ConstraintViolation<SignUpForm>> violations = validatorInjected.validate(signUpForm);
        log.error(violations.toString());

//        Account account = accountService.processNewAccount(testsignUpForm);

//        assertThat(account.getUsername()).isEqualTo("test546");
//        assertThat(account.getIdentity()).isEqualTo("wer");
//        assertThat(account.getPassword()).isEqualTo("1234");
//        assertThat(account.getEmail()).isEqualTo("sjekflseef.@naver.com");

    }

    @Test
    public void saveNewAccount(){

    }

    @Test
    public void sendsignUpConfirmEmail(){

    }

    @Test
    public void login(){

    }

    @Test
    public void loadUserByUsername(){

    }

    @Test
    public void completeSignUp(){

    }

    @Test
    public void updatePassword(){

    }

    @Test
    public void deleteAccount(){

    }

    @Test
    public void updateIdentity(){

    }

}
