package com.finalproject.shelter.model.entity;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AccountRequestDto {

    private String username;

    private String password;

    private String password_confirm;

    @Builder
    public AccountRequestDto(String username, String password, String password_confirm){
        this.username = username;
        this.password = password;
        this.password_confirm = password_confirm;
    }
}