package com.finalproject.shelter.businessLayer.settings.form.identity;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
public class IdentityForm {
    @NotBlank
    @Length(min = 3, max = 20)
    @Pattern(regexp = "^[ㄱ-ㅎ가-힣a-z0-9_-]{3,20}$")
    private String identity;

    public IdentityForm(String identity) {
        this.identity = identity;
    }
}
