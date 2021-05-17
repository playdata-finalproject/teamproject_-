package com.finalproject.shelter.businessLayer.settings.form.username;

import com.finalproject.shelter.domainModelLayer.model.entity.userDomain.Account;
import com.finalproject.shelter.domainModelLayer.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class UsernameFormValidator implements Validator {

    private final AccountRepository accountRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return UsernameForm.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UsernameForm usernameForm = (UsernameForm) target;
        Account byUsername = accountRepository.findByUsername(usernameForm.getUsername());
        if (byUsername != null) {
            errors.rejectValue("username", "wrong.value", "입력하신 이름을 사용할 수 없습니다.");
        }
    }
}
