package com.finalproject.shelter.service.Api;

import com.finalproject.shelter.model.entity.Role;
import com.finalproject.shelter.model.entity.User;
import com.finalproject.shelter.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    //여기 부터!
    public User save(User user){
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        user.setIdentity(user.getIdentity());
        user.setEmail(user.getEmail());
        user.setNickname(user.getNickname());
        user.setKakaoId(1L);
        user.setLoginFailCount(0);
        user.setCreatedAt(LocalDateTime.now());
        user.setUncreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        user.setLastLoginAt(LocalDateTime.now());
        user.setUpdatedBy("");
        user.setEnabled(true);
        Role role = new Role();
        role.setId(1l);
        user.getRoles().add(role);
        //validateDuplicateIdentity(user);
        return userRepository.save(user);
    }

//    private void validateDuplicateIdentity(User user){
//        if(user.getIdentity() != null) {
//            throw new IllegalStateException("이미 존재하는 회원입니다.");
//        }
//    }

}