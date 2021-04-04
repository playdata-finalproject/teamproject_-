package com.finalproject.shelter.controller.page;

import com.finalproject.shelter.model.entity.Account;
import com.finalproject.shelter.model.entity.CurrentUser;
import com.finalproject.shelter.repository.AccountRepository;
import com.finalproject.shelter.service.AccountService;
import com.finalproject.shelter.settings.form.SignUpForm;
import com.finalproject.shelter.settings.form.SignUpFormValidator;
import lombok.RequiredArgsConstructor;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Objects;

@Controller
@RequestMapping("/account")
@RequiredArgsConstructor
public class AccountController {

    private final SignUpFormValidator signUpFormValidator;
    private final AccountService accountService;
    private final AccountRepository accountRepository;
    private final String HOME = "/main";
    //private final String EMAIL_CONFIRM_VIEW = "account/emailConfirm";

    @InitBinder("signUpForm")
    // signUpForm 데이터를 받을 때 데이터를 자동으로 바인딩 해준다.
    // 여기선 validator 가 자동으로 실행된다.
    public void initBinder(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(signUpFormValidator);
    }

    @GetMapping("/login")
    public String login() {
        return "account/login";
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("signUpForm",new SignUpForm()); // class 이름과 동일한 attribute를 사용할 경우 생략 가능
        return "account/register";
    }

    @PostMapping("/register")
    public String register(@Valid SignUpForm signUpForm, Errors errors){
        if (errors.hasErrors()) {
            return "account/register";
        }
        Account account = accountService.processNewAccount(signUpForm);
        accountService.login(account);
        return "redirect:"+HOME;
    }

    @GetMapping("/check-email-token")
    public String checkEmailToken(String token, String email, Model model) {
        Account account = accountRepository.findByEmail(email);
        String view = "account/checked-email";
        if (Objects.isNull(account)) {
            model.addAttribute("error", "wrong.email");
            return view;
        }

        if (!account.isValidToken(token)) {
            model.addAttribute("error", "wrong.token");
            return view;
        }
        accountService.completeSignUp(account);
        model.addAttribute("numberOfUser", accountRepository.count());
        model.addAttribute("identity", account.getIdentity());
        return view;
    }

    @GetMapping("/check-email")
    public String recheckEmail(@CurrentUser Account account, Model model) {
        model.addAttribute("email", account.getEmail());
        model.addAttribute("identity", account.getIdentity());
        return "account/check-email";
    }


    @GetMapping("/resend-confirm-email")
    public String resendConfirmEmail(@CurrentUser Account account, Model model) {
        if (!account.canSendConfirmEmail()) {
            model.addAttribute("error", "인증 이메일은 1시간에 한번만 전송할 수 있습니다.");
            model.addAttribute("email", account.getEmail());
            return "account/checked-email";
        }

        accountService.sendSignUpConfirmEmail(account);
        return "redirect:"+HOME;
    }

    @GetMapping("/profile/{identity}")
    public String viewProfile(@PathVariable String identity, Model model, @CurrentUser Account account) {
        Account byIdentity = accountRepository.findByIdentity(identity);

        if (byIdentity == null) {
            throw new IllegalArgumentException(identity + " 에 해당하는 사용자가 없습니다.");
        }
        model.addAttribute(byIdentity); // 기본값은 model에 들어간 객체의 타입이 이름으로 들어간다.
        model.addAttribute("isOwner", account.equals(byIdentity));
        return "account/profile";
    }

}