package com.finalproject.shelter.repository;

import com.finalproject.shelter.ShelterApplicationTests;
import com.finalproject.shelter.model.entity.Account;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@Transactional
public class AccountRepositoryTest extends ShelterApplicationTests {

    @Autowired
    private AccountRepository accountRepository;

    @Test
    public void create(){

    }

    @DisplayName("레코드 username 조회 테스트")
    @Test
    public void findUsername(){

        Account account = accountRepository.findByUsername("test12");

        assertThat(account).isNotEqualTo(null);
        assertThat(account.getUsername()).isEqualTo("test12");
    }

    @DisplayName("레코드 identity 조회 테스트")
    @Test
    public void findIdentity(){

        Account account = accountRepository.findByIdentity("봉구스박보검");

        assertThat(account).isNotEqualTo(null);
        assertThat(account.getIdentity()).isEqualTo("봉구스박보검");
    }

    @DisplayName("레코드 email 조회 테스트")
    @Test
    public void findEmail(){

        Account account = accountRepository.findByEmail("ssfe@naver.com");

        assertThat(account).isNotEqualTo(null);
        assertThat(account.getEmail()).isEqualTo("ssfe@naver.com");
    }


}
