package com.finalproject.shelter.settings.form;

import com.finalproject.shelter.model.entity.Account;
import com.finalproject.shelter.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class IdentityFormValidator implements Validator {

    private final UserRepository userRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return IdentityForm.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        IdentityForm identityForm = (IdentityForm) target;
        Account byIdentity = userRepository.findByIdentity(identityForm.getIdentity());
        if (byIdentity != null) {
            errors.rejectValue("identity", "wrong.value", "입력하신 아이디를 사용할 수 없습니다.");
        }
    }
}
