package com.finalproject.shelter.model.entity;

import com.finalproject.shelter.model.entity.Account;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.List;


public class UserAccount extends User {

    private Account account;
    public UserAccount(Account account) {
        super(account.getUsername(), account.getPassword(), List.of(new SimpleGrantedAuthority("ROLE_Account")));
        this.account = account;
    }
}
