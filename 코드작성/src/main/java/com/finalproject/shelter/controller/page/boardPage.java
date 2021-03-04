package com.finalproject.shelter.controller.page;

import com.finalproject.shelter.model.entity.Categorytable;
import com.finalproject.shelter.repository.CategorytableRepository;
import com.finalproject.shelter.service.Logic.CategoryLogicService;
import com.finalproject.shelter.service.Logic.CategorytableLogicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@Controller
@RequestMapping("/board")
public class boardPage {

    @Autowired
    private CategoryLogicService categoryLogicService;



    @GetMapping("")
    public ModelAndView boardlist(HttpServletRequest request){
        String id = request.getParameter("id");

        return new ModelAndView("/pages/list")
                .addObject("bestTitle",categoryLogicService.readtitle(id));
    }

    @GetMapping("/view")
    public ModelAndView listview(){ return new ModelAndView("/pages/view");}
}
