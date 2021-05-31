package com.finalproject.shelter.presentation.controller.view;

import com.finalproject.shelter.domain.model.entity.userDomain.Account;
import com.finalproject.shelter.domain.model.entity.userDomain.CurrentUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Objects;

@Controller
//@RequestMapping("/main")
public class MainPageController {

    @GetMapping("/main")
    public String home(@CurrentUser Account account, Model model) {
        if (!Objects.isNull(account)) {
            model.addAttribute(account);
        }
        return "pages/index";
    }

}