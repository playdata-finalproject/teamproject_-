package com.finalproject.shelter.controller.page;

import com.finalproject.shelter.model.entity.Account;
import com.finalproject.shelter.model.entity.CurrentUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/main")
public class MainPageController {

    @GetMapping("")
    public ModelAndView mainpage(){ return new ModelAndView("pages/index");}

//    @GetMapping("")
//    public String home(Account account, Model model) {
//        if(account != null){
//            model.addAttribute(account);
//        }
//        return "pages/index";
//    }
}