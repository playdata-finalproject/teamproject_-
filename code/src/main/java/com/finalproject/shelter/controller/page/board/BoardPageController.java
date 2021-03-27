package com.finalproject.shelter.controller.page.board;

import com.finalproject.shelter.model.entity.Board;
import com.finalproject.shelter.service.Logic.BoardLogicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequestMapping("/board")
public class BoardPageController {

    @Autowired
    private BoardLogicService boardLogicService;

    @GetMapping("")
    public String findboardlist(@RequestParam(value = "id") String id,
                                  @PageableDefault(size = 10) Pageable pageable,
                                      @RequestParam(value = "select",required = false, defaultValue = "") String select,
                                      @RequestParam(value = "searchText",required = false, defaultValue = "") String searchText,
                                      Model model
                                      ){

        Page<Board> boardlist = boardLogicService.findCategoryIdTitleContents(id,select,searchText,pageable);
        List<Board> weekview = boardLogicService.bestweekview(id);
        List<Board> monthview = boardLogicService.bestmonthview(id);
        Board eachboard = boardLogicService.readCategory(id);

        int startPage = Math.max(1,boardlist.getPageable().getPageNumber() -4);
        int endPage = Math.min(boardlist.getTotalPages(),boardlist.getPageable().getPageNumber()+4);

        model.addAttribute("boardlist",boardlist);
        model.addAttribute("startPage",startPage);
        model.addAttribute("endPage",endPage);
        model.addAttribute("eachboard",eachboard);
        model.addAttribute("weekview",weekview);
        model.addAttribute("monthview",monthview);

        return "pages/list";
    }

    @GetMapping("/delete")
    public String deleteboard(@RequestParam(value = "id") String ids){

        String id = boardLogicService.deleteid(ids);

        if (id!=null) {
            return "redirect:/board?id=" + id + "&page=0";
        }else{
            return "redirect:/main";
        }
    }

}