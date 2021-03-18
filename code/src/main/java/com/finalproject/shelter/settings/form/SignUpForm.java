package com.finalproject.shelter.settings.form;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
public class SignUpForm {
    @Length(min = 4, max = 50)
    @NotBlank
    private String username;

    @NotBlank
    private String identity;

    @Length(min = 8, max = 50)
    @NotBlank
    private String password;


    @Email
    @NotBlank
    private String email;


}
