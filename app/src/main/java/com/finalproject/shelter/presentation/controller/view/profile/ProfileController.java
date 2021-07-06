package com.finalproject.shelter.presentation.controller.view.profile;

import com.finalproject.shelter.domain.model.entity.userDomain.Account;
import com.finalproject.shelter.domain.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/profile")
@RequiredArgsConstructor
public class ProfileController {

    private final AccountRepository accountRepository;

    @GetMapping("/{username}")
    public String view(@PathVariable String username, Model model) {
        Account byUsername = accountRepository.findByUsername(username);
        isByUsernameEqualsNull(byUsername);
        model.addAttribute(byUsername);
        model.addAttribute("isOwner", 1);
        return "account/profile";
    }

    private void isByUsernameEqualsNull(Account byUsername) {
        if (byUsername == null) {
            throw new IllegalArgumentException("Username" + " 에 해당하는 사용자가 없습니다.");
        }
    }

}
