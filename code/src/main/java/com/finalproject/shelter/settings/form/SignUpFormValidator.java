package com.finalproject.shelter.settings.form;


import com.finalproject.shelter.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class SignUpFormValidator implements Validator {

    private final UserRepository userRepository;


    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.isAssignableFrom(SignUpForm.class);
    }

    @Override
    public void validate(Object object, Errors errors) {
        // TODO email, nickname
        SignUpForm signUpForm = (SignUpForm) object;
        if (userRepository.existsByEmail(signUpForm.getEmail())) {
            errors.rejectValue("email", "invalid.email", new Object[]{signUpForm.getEmail()}, "이미 사용중인 이메일 입니다.");
        }
        if (userRepository.existsByNickname(signUpForm.getNickname())) {
            errors.rejectValue("nickname", "invalid.nickname", new Object[]{signUpForm.getNickname()}, "이미 사용중인 닉네임 입니다.");
        }

        if (userRepository.existsByNickname(signUpForm.getIdentity())) {
            errors.rejectValue("identity", "invalid.identity", new Object[]{signUpForm.getIdentity()}, "이미 사용중인 아이디입니다.");
        }

        if (!signUpForm.getNewPasswordConfirm().equals(signUpForm.getNewPasswordConfirm())) {
            errors.rejectValue("newPasswordConfirm", "wrong.value", "입력한 새 패스워드가 일치하지 않습니다.");
        }
    }

}
