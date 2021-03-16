package com.finalproject.shelter.controller.page;

import com.finalproject.shelter.model.entity.Account;
import com.finalproject.shelter.repository.AccountRepository;
import com.finalproject.shelter.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/profile")
@RequiredArgsConstructor
public class ProfileController {

    private final AccountService accountService;
    private final AccountRepository accountRepository;
    private final ModelMapper modelMapper;

    @GetMapping("/{username}")
    public String viewProfile(@PathVariable String username, Model model){
        Account byUsername = accountRepository.findByUsername(username);

        if (username == null) {
            throw new IllegalArgumentException(username + " 에 해당하는 사용자가 없습니다.");
        }

        model.addAttribute(byUsername); // 기본값은 model에 들어간 객체의 타입이 이름으로 들어간다.
        model.addAttribute("isOwner",1);
        return "account/profile";
    }

}
