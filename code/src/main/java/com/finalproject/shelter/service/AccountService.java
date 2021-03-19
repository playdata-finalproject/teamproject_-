package com.finalproject.shelter.service;

import com.finalproject.shelter.config.AppProperties;
import com.finalproject.shelter.model.entity.Account;
import com.finalproject.shelter.model.entity.Role;
import com.finalproject.shelter.model.entity.UserAccount;
import com.finalproject.shelter.repository.AccountRepository;
import com.finalproject.shelter.settings.form.SignUpForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.TemplateEngine;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = false)
@Slf4j
public class AccountService implements UserDetailsService {

    private final AccountRepository accountRepository;
    private final TemplateEngine templateEngine;
    private final AppProperties appProperties;
    private final ModelMapper modelMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;
//    @Autowired
//    private final EmailService emailService;

    @Transactional
    public Account processNewAccount(@Valid SignUpForm signUpForm) {
        //회원 가입 처리
        Account newAccount = saveNewAccount(signUpForm);
        //Mail Send
        return newAccount;
    }

    private Account saveNewAccount(@Valid SignUpForm signUpForm) {
        Account account = modelMapper.map(signUpForm, Account.class);
        return save(account);
    }


    //여기 부터!
    public Account save(Account account){
        String encodedPassword = passwordEncoder.encode(account.getPassword());
        account.setPassword(encodedPassword);
        account.setIdentity(account.getIdentity());
        account.setEmail(account.getEmail());
        account.setNickname("aaaa");
        account.setKakaoId(1L);
        account.setLoginFailCount(0);
        account.setCreatedAt(LocalDate.now());
        account.setUncreatedAt(LocalDate.now());
        account.setUpdatedAt(LocalDate.now());
        account.generateEmailCheckToken();  
        account.setLastLoginAt(LocalDate.now());
        account.setUpdatedBy("");
        account.setEnabled(true);
        Role role = new Role();
        role.setId(1l);
        account.getRoles().add(role);

        //tentative email auth set
        account.completeSignUp();
        //validateDuplicateIdentity(account);
        return accountRepository.save(account);
    }

    public void login(Account account) {
        // username과 password를 조합해서 UsernamePasswordAuthenticationToken 인스턴스를 생성
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                new UserAccount(account),
                account.getPassword(),
                List.of(new SimpleGrantedAuthority("ROLE_USER")));
        SecurityContextHolder.getContext().setAuthentication(token);
    }

    @Transactional
    public void completeSignUp(Account account) {
        account.completeSignUp();
    }

    @Override
    public UserDetails loadUserByUsername(String usernameOrIdentity) throws UsernameNotFoundException { // 로그인 처리, Spring Security에서 자동으로 처리함.
        Account account = accountRepository.findByUsername(usernameOrIdentity);
        if (account == null) {
            account = accountRepository.findByIdentity(usernameOrIdentity);
        }

        if (account == null) {
            throw new UsernameNotFoundException(usernameOrIdentity);
        }
        return new UserAccount(account);  // Principal 객체 리턴
    }


    @Transactional
    public void updatePassword(Account account, String newPassword) {
        account.setPassword(passwordEncoder.encode(newPassword));
        accountRepository.save(account);
        login(account);
    }

    public void updateIdentity(Account account, String identity) {
        account.setIdentity(identity);
        accountRepository.save(account);
        login(account);
    }
}