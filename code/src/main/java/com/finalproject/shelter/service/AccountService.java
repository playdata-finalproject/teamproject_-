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

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.TemplateEngine;

import javax.validation.Valid;
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
        signUpForm.setPassword(passwordEncoder.encode(signUpForm.getPassword()));
        Account account = modelMapper.map(signUpForm, Account.class);
        account.generateEmailCheckToken();
        return accountRepository.save(account);
    }

    @Autowired
    private PasswordEncoder passwordEncoder;
    //여기 부터!
    public Account save(Account account){
        String encodedPassword = passwordEncoder.encode(account.getPassword());
        account.setPassword(encodedPassword);
        account.setIdentity(account.getIdentity());
        account.setEmail(account.getEmail());
        account.setNickname("aaaa");
        account.setKakaoId(1L);
        account.setLoginFailCount(0);
        account.setCreatedAt(LocalDateTime.now());
        account.setUncreatedAt(LocalDateTime.now());
        account.setUpdatedAt(LocalDateTime.now());
        account.setLastLoginAt(LocalDateTime.now());
        account.setUpdatedBy("");
        account.setEnabled(false);
        Role role = new Role();
        role.setId(1l);
        account.getRoles().add(role);
        //validateDuplicateIdentity(account);
        return accountRepository.save(account);
    }

    public void login(Account account) {
        // username과 password를 조합해서 UsernamePasswordAuthenticationToken 인스턴스를 생성
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                new UserAccount(account),
                account.getPassword(),
                List.of(new SimpleGrantedAuthority("ROLE_USER")));

        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(token);
    }

    @Transactional
    public void completeSignUp(Account account) {
        account.completeSignUp();
    }

    @Override
    public UserDetails loadUserByUsername(String usernameOrIdentity) throws UsernameNotFoundException { // 로그인 처리, Spring Security에서 자동으로 처리함.
        Account account = accountRepository.findByUsername(usernameOrIdentity);
        //Account  확인
        if (account == null) {
            account = accountRepository.findByIdentity(usernameOrIdentity);
            //Account  확인
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
    }

    public void updateIdentity(Account account, String identity) {
        account.setIdentity(identity);
        accountRepository.save(account);
    }
}