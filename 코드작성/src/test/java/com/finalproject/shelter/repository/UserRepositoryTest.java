package com.finalproject.shelter.repository;

import com.finalproject.shelter.ShelterApplicationTests;
import com.finalproject.shelter.model.entity.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;


public class UserRepositoryTest extends ShelterApplicationTests {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void create(){

        User user = User.builder()
                .userId("qwer")
                .password("1234")
                .name("바보")
                .createdAt(LocalDateTime.now())
                .build();

        User newuser = userRepository.save(user);
        Assertions.assertNotNull(newuser);
        System.out.println("User : " + newuser);
    }

}
