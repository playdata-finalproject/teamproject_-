package com.finalproject.shelter.controller.page;

import com.finalproject.shelter.model.entity.Account;
import com.finalproject.shelter.model.entity.CurrentUser;
import com.finalproject.shelter.repository.AccountRepository;
import com.finalproject.shelter.service.AccountService;
import com.finalproject.shelter.settings.form.*;
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
//    private final AccountRepository accountRepository;
    private final IdentityFormValidator identityFormValidator;

    private final ModelMapper modelMapper;

    @InitBinder("passwordForm")
    public void passwordFormInitBinder(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(new PasswordFormValidator());
    }


    @InitBinder("identityForm")
    public void identityFormInitBinder(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(identityFormValidator);
    }

    @GetMapping(SETTINGS_PASSWORD_URL)
    public String updatePasswordForm(@CurrentUser Account accounts, Model model) {
        model.addAttribute(accounts);
        model.addAttribute(new PasswordForm());
        return SETTINGS_PASSWORD_VIEW_NAME;
    }

    @PostMapping(SETTINGS_PASSWORD_URL)
    public String updatePassword(@CurrentUser Account accounts, @Valid PasswordForm passwordForm, Errors errors,
                                 Model model, RedirectAttributes redirectAttributes) {
        if (errors.hasErrors()) {
            model.addAttribute(accounts);
            return SETTINGS_PASSWORD_VIEW_NAME;
        }

        accountService.updatePassword(accounts, passwordForm.getNewPassword());
        redirectAttributes.addFlashAttribute("message", "패스워드를 변경하였습니다.");
        return "redirect:" + SETTINGS_PASSWORD_URL;
    }
    @GetMapping(SETTINGS_ACCOUNT_URL)
    public String updateAccountForm(@CurrentUser Account accounts, Model model) {
        model.addAttribute(accounts);
        model.addAttribute(modelMapper.map(accounts, IdentityForm.class));
        return SETTINGS_ACCOUNT_VIEW_NAME;
    }

    @PostMapping(SETTINGS_ACCOUNT_URL)
    public String updateAccount(@CurrentUser Account accounts, @Valid IdentityForm identityForm, Errors errors,
                                Model model, RedirectAttributes attributes) {
        if (errors.hasErrors()) {
            model.addAttribute(accounts);
            return SETTINGS_ACCOUNT_VIEW_NAME;
        }

        accountService.updateIdentity(accounts, identityForm.getIdentity());
        attributes.addFlashAttribute("message", "닉네임을 수정했습니다.");
        return "redirect:" + SETTINGS_ACCOUNT_URL;
    }
}
