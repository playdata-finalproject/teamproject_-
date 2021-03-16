package com.finalproject.shelter.controller.page;

import com.finalproject.shelter.model.entity.Account;
import com.finalproject.shelter.repository.AccountRepository;
import com.finalproject.shelter.service.AccountService;
import com.finalproject.shelter.settings.form.NicknameForm;
import com.finalproject.shelter.settings.form.NicknameFormValidator;
import com.finalproject.shelter.settings.form.PasswordForm;
import com.finalproject.shelter.settings.form.PasswordFormValidator;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
//@RequestMapping("settings")
@RequiredArgsConstructor
public class ProfileSettingController {
    static final String SETTINGS_PASSWORD_VIEW_NAME = "account/password";
    static final String SETTINGS_PASSWORD_URL = "/settings/password";
    static final String SETTINGS_ACCOUNT_VIEW_NAME = "account/account";
    static final String SETTINGS_ACCOUNT_URL = "/settings/account";

    private final AccountService accountService;
    private final AccountRepository accountRepository;
    private final NicknameFormValidator nicknameValidator;
    private final ModelMapper modelMapper;

    @InitBinder("passwordForm")
    public void passwordFormInitBinder(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(new PasswordFormValidator());
    }


    @InitBinder("nicknameForm")
    public void nicknameFormInitBinder(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(nicknameValidator);
    }

//    @GetMapping(SETTINGS_PROFILE_URL)
//    public String profileForm(Account account, Model model) {
//        model.addAttribute(account);
//        model.addAttribute(modelMapper.map(account, Profile.class));
//        return SETTINGS_PROFILE_VIEW_NAME;
//    }

//    @PostMapping(SETTINGS_PROFILE_URL)
//    public String updateProfile( Account account, @Valid Profile profile, Errors errors,
//                                Model model, RedirectAttributes attributes) {
//        // 현재 account는 detached 상태이다. 한번이라도 영속성 컨텍스트에 들어간 객체 (id값이 있는 상태)
//        if (errors.hasErrors()) {
//            model.addAttribute(account);
//            return SETTINGS_PROFILE_VIEW_NAME;
//        }
//
//        accountService.updateProfile(account, profile);
//        attributes.addFlashAttribute("message", "프로필을 수정했습니다.");// 리다이텍트 시 한번 사용하고 사라지는 데이터, 자동으로 Model에 들어간다.
//        return "redirect:" + SETTINGS_PROFILE_URL;
//    }
    @GetMapping(SETTINGS_PASSWORD_URL)
    public String updatePasswordForm( Account account, Model model) {
        model.addAttribute(account);
        model.addAttribute(new PasswordForm());
        return SETTINGS_PASSWORD_VIEW_NAME;
    }

    @PostMapping(SETTINGS_PASSWORD_URL)
    public String updatePassword( Account account, @Valid PasswordForm passwordForm, Errors errors,
                                 Model model, RedirectAttributes redirectAttributes) {
        if (errors.hasErrors()) {
            model.addAttribute(account);
            return SETTINGS_PASSWORD_VIEW_NAME;
        }

        accountService.updatePassword(account, passwordForm.getNewPassword());
        redirectAttributes.addFlashAttribute("message", "패스워드를 변경하였습니다.");
        return "redirect:" + SETTINGS_PASSWORD_URL;
    }
    @GetMapping(SETTINGS_ACCOUNT_URL)
    public String updateAccountForm( Account account, Model model) {
        model.addAttribute(account);
        model.addAttribute(modelMapper.map(account, NicknameForm.class));
        return SETTINGS_ACCOUNT_VIEW_NAME;
    }

    @PostMapping(SETTINGS_ACCOUNT_URL)
    public String updateAccount( Account account, @Valid NicknameForm nicknameForm, Errors errors,
                                Model model, RedirectAttributes attributes) {
        if (errors.hasErrors()) {
            model.addAttribute(account);
            return SETTINGS_ACCOUNT_VIEW_NAME;
        }

        accountService.updateNickname(account, nicknameForm.getNickname());
        attributes.addFlashAttribute("message", "닉네임을 수정했습니다.");
        return "redirect:" + SETTINGS_ACCOUNT_URL;
    }
}
