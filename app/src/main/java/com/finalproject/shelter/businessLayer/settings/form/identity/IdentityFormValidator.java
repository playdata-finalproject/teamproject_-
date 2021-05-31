package com.finalproject.shelter.businessLayer.settings.form.identity;

import com.finalproject.shelter.domainModelLayer.model.entity.userDomain.Account;
import com.finalproject.shelter.domainModelLayer.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class IdentityFormValidator implements Validator {

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
            errors.rejectValue("identity", "wrong.value", "입력하신 아이디를 사용할 수 없습니다.");
        }
    }
}
