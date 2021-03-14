package com.finalproject.shelter.controller.page;

import com.finalproject.shelter.model.entity.Account;
import com.finalproject.shelter.model.entity.CurrentUser;
import com.finalproject.shelter.model.entity.Profile;
import com.finalproject.shelter.repository.UserRepository;
import com.finalproject.shelter.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import javax.validation.Valid;

@Controller
@RequestMapping("/profile")
@RequiredArgsConstructor
public class ProfileController {

    private final AccountService accountService;
    private final UserRepository userRepository;
    static final String SETTINGS_PROFILE_VIEW_NAME = "settings/profile";
    static final String SETTINGS_PROFILE_URL = "/settings/profile";
    private final ModelMapper modelMapper;

    @GetMapping("/{username}")
    public String viewProfile(@PathVariable String username, Model model, @CurrentUser Account account) {
        Account byUsername = userRepository.findByUsername(username);

        if (username == null) {
            throw new IllegalArgumentException(username + " 에 해당하는 사용자가 없습니다.");
        }

        model.addAttribute(byUsername); // 기본값은 model에 들어간 객체의 타입이 이름으로 들어간다.
        model.addAttribute("isOwner", byUsername.equals(account));
        return "account/profile";
    }

    @GetMapping(SETTINGS_PROFILE_URL)
    public String profileForm(@CurrentUser Account account, Model model) {
        model.addAttribute(account);
        model.addAttribute(modelMapper.map(account, Profile.class));
        return SETTINGS_PROFILE_VIEW_NAME;
    }

    @PostMapping(SETTINGS_PROFILE_URL)
    public String updateProfile(@CurrentUser Account account, @Valid Profile profile, Errors errors,
                                Model model, RedirectAttributes attributes) {
        // 현재 account는 detached 상태이다. 한번이라도 영속성 컨텍스트에 들어간 객체 (id값이 있는 상태)
        if (errors.hasErrors()) {
            model.addAttribute(account);
            return SETTINGS_PROFILE_VIEW_NAME;
        }

        accountService.updateProfile(account, profile);
        attributes.addFlashAttribute("message", "프로필을 수정했습니다.");// 리다이텍트 시 한번 사용하고 사라지는 데이터, 자동으로 Model에 들어간다.
        return "redirect:" + SETTINGS_PROFILE_URL;
    }
}
