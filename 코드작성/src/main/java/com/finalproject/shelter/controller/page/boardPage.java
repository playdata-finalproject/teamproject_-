package com.finalproject.shelter.controller.page;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/board")
public class boardPage {

    @GetMapping("")
    public ModelAndView boardlist(){ return new ModelAndView("/pages/list");}

    @GetMapping("/view")
    public ModelAndView listview(){ return new ModelAndView("/pages/view");}
}
