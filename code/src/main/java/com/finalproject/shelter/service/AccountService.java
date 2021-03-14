package com.finalproject.shelter.service;

import com.finalproject.shelter.config.AppProperties;
import com.finalproject.shelter.model.entity.Account;
import com.finalproject.shelter.model.entity.Profile;
import com.finalproject.shelter.model.entity.Role;
import com.finalproject.shelter.model.entity.UserAccount;
import com.finalproject.shelter.repository.UserRepository;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class AccountService implements UserDetailsService {

    private final UserRepository userRepository;
    private final TemplateEngine templateEngine;
    private final AppProperties appProperties;
    private final ModelMapper modelMapper;

//    @Autowired
//    private final EmailService emailService;

    @Autowired
    private PasswordEncoder passwordEncoder;
    //여기 부터!
    public Account save(Account user){
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
        user.setEnabled(false);
        Role role = new Role();
        role.setId(1l);
        user.getRoles().add(role);
        //validateDuplicateIdentity(user);
        return userRepository.save(user);
    }



//    public void sendSignUpConfirmEmail(Account newAccount) {
//        Context context = new Context();
//        context.setVariable("link", "/check-email-token?token=" + newAccount.getEmailCheckToken() +
//                "&email=" + newAccount.getEmail());
//        context.setVariable("nickname", newAccount.getNickname());
//        context.setVariable("linkName", "이메일 인증하기");
//        context.setVariable("message", "스터디올래 서비스를 사용하려면 링크를 클릭하세요.");
//        context.setVariable("host", appProperties.getHost());
//        String message = templateEngine.process("mail/simple-link", context);
//
//        EmailMessage emailMessage = EmailMessage.builder()
//                .to(newAccount.getEmail())
//                .subject("스터디올래, 회원 가입 인증")
//                .message(message)
//                .build();
//
//        emailService.sendEmail(emailMessage);
//    }

    @Transactional
    public void completeSignUp(Account account) {
        account.completeSignUp();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException { // 로그인 처리, Spring Security에서 자동으로 처리함.
        Account account = userRepository.findByUsername(username);
//        if (account == null) {
//            account = userRepository.findByIdentity(account.getIdentity());
//        }

        if (account == null) {
            throw new UsernameNotFoundException(username);
        }
        return new UserAccount(account);  // Principal 객체 리턴
    }
    public void updateProfile(Account account, Profile profile) {
        modelMapper.map(profile, account);
//        account.setUrl(profile.getUrl());
//        account.setOccupation(profile.getOccupation());
//        account.setBio(profile.getBio());
//        account.setLocation(profile.getLocation());
//        account.setProfileImage(profile.getProfileImage());
        userRepository.save(account);
    }
}