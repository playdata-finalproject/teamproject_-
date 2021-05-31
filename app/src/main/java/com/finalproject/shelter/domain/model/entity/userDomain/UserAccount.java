package com.finalproject.shelter.domain.model.entity.userDomain;

import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;


import java.util.List;

@Getter
public class UserAccount extends User {

    private Account account; //왜 연동안되는지 모르겠음!!

    public UserAccount(Account account){
        super(account.getUsername(), account.getPassword(), List.of(new SimpleGrantedAuthority("ROLE_USER")));
        this.account = account;
    }
}
