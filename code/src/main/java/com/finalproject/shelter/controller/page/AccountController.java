package com.finalproject.shelter.controller.page;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.finalproject.shelter.model.entity.Account;
import com.finalproject.shelter.model.entity.CurrentUser;
import com.finalproject.shelter.model.entity.Profile;
import com.finalproject.shelter.repository.UserRepository;
import com.finalproject.shelter.service.AccountService;
import com.finalproject.shelter.settings.form.SignUpForm;
import com.finalproject.shelter.settings.form.SignUpFormValidator;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.Objects;

@Controller
@RequestMapping("/account")
@RequiredArgsConstructor
public class AccountController {

    private final SignUpFormValidator signUpFormValidator;
    @Autowired
    private final AccountService accountService;
    private final UserRepository userRepository;

    private final String HOME = "redirect:/main";
    private final String SIGN_UP_VIEW = "sign/login";
    //private final String EMAIL_CONFIRM_VIEW = "account/emailConfirm";

    @InitBinder("signUpForm") // signUpForm 데이터를 받을 때 데이터를 자동으로 바인딩 해준다. 여기선 validator 가 자동으로 실행된다.
    public void initBinder(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(signUpFormValidator);
    }


    @GetMapping("/login")
    public String login(){
        return "account/login";
    }

    @GetMapping ("/register")
    public String register(Model model){
        model.addAttribute(new SignUpForm());
        return "account/register";
    }

    @PostMapping("/register")
    public String register(Account user, RedirectAttributes rttr, @Valid SignUpForm signUpForm, Errors errors) throws Exception{
        if (errors.hasErrors()) {
            return "account/register";
        }
        accountService.save(user);
        rttr.addFlashAttribute("msg" , "가입시 사용한 이메일로 인증해주세요");
        return "redirect:/login";
    }
    @GetMapping("/check-email-token")
    public String checkEmailToken(String token, String email, Model model) {
        Account account = userRepository.findByEmail(email);
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
        model.addAttribute("numberOfUser", userRepository.count());
        model.addAttribute("nickname", account.getNickname());
        return view;
    }




//    @GetMapping("/check-email")
//    public String checkEmail(@CurrentUser Account account, Model model) {
//        model.addAttribute("email", account.getEmail());
//        return "account/check-email";
//    }

//    @GetMapping("/resend-confirm-email")
//    public String resendConfirmEmail(@CurrentUser Account account, Model model) {
//        if (!account.canSendConfirmEmail()) {
//            model.addAttribute("error", "인증 이메일은 1시간에 한번만 전송할 수 있습니다.");
//            model.addAttribute("email", account.getEmail());
//        }
//
//        accountService.sendSignUpConfirmEmail(account);
//        return "redirect:/";
//    }

}
