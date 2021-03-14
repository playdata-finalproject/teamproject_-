package com.finalproject.shelter.settings.form;

import com.finalproject.shelter.model.entity.Account;
import com.finalproject.shelter.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class UsernameFormValidator implements Validator {

    private final UserRepository userRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return UsernameForm.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UsernameForm usernameForm = (UsernameForm) target;
        Account byUsername = userRepository.findByUsername(usernameForm.getUsername());
        if (byUsername != null) {
            errors.rejectValue("username", "wrong.value", "입력하신 이름을 사용할 수 없습니다.");
        }
    }
}
