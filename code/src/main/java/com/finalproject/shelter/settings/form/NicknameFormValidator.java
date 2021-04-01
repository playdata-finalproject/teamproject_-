package com.finalproject.shelter.settings.form;

import com.finalproject.shelter.model.entity.Account;
import com.finalproject.shelter.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class NicknameFormValidator implements Validator {

    private final AccountRepository accountRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return IdentityForm.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        IdentityForm identityForm = (IdentityForm) target;
        Account byIdentity = accountRepository.findByIdentity(identityForm.getIdentity());
        if (byIdentity != null) {
            errors.rejectValue("nickname", "wrong.value", "입력하신 닉네임을 사용할 수 없습니다.");
        }
    }
}