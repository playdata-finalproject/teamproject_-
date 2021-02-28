package com.finalproject.shelter.controller.page;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/main")
public class MainPage {

    @GetMapping("")
    public ModelAndView mainpage(){ return new ModelAndView("/pages/index");}

}
