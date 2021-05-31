package com.finalproject.shelter.businessLayer.settings.form.delete;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class DeleteFormValidator implements Validator {

    @Override
    public boolean supports(Class<?> aClass) {
        return DeleteForm.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        DeleteForm deleteForm = (DeleteForm) o;
        String identifier = "corinshelter";
        if (!deleteForm.getDeleteConfirm().equals(identifier)) {
            errors.rejectValue("deleteConfirm", "wrong.value", "패스워드가 틀려, 회원 탈퇴를 할 수 없습니다.");
        }
    }
}
