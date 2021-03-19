package com.finalproject.shelter.model.entity;

import lombok.Getter;
import com.finalproject.shelter.model.entity.Account;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;


import java.util.List;

@Getter
public class UserAccount extends User {

    private Account account; //왜 연동안되는지 모르겠음!!

    public UserAccount(Account account){
        super(account.getIdentity(), account.getPassword(), List.of(new SimpleGrantedAuthority("ROLE_USER")));
        this.account = account;
    }
}
