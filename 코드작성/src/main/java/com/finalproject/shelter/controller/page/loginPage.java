package com.finalproject.shelter.controller.page;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/login")
public class loginPage {


    @GetMapping("")
    public ModelAndView loginpage(){ return new ModelAndView("/pages/login");}

    @GetMapping("/join")
    public ModelAndView joinpage(){ return new ModelAndView("/pages/join");}

}
