package com.finalproject.shelter.presentationLayer.controller.view.board;

import com.finalproject.shelter.domainModelLayer.model.entity.noticationDomain.Board;
import com.finalproject.shelter.domainModelLayer.repository.AccountRepository;
import com.finalproject.shelter.business.service.logic.boardlogic.BoardLogicService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/board/form")
@Slf4j
public class BoardFormPageController {

    @Autowired
    private BoardLogicService boardLogicService;

    @Autowired
    private AccountRepository accountRepository;

    @GetMapping("")
    public String writeview(
            @RequestParam(value = "boardid",defaultValue = "") String boardid,
            @RequestParam(value = "name",defaultValue = "") String name,
            @RequestParam(value = "categoryid",defaultValue = "") String categoryid,
            Model model, Error error
    ){
        

        if (name.equals("write")){
            Board board = boardLogicService.readCategory(categoryid);
            Board board1 = boardLogicService.newuserboard(board,accountRepository); //error

            model.addAttribute("eachboard",board);
            model.addAttribute("board",board1);

            return "pages/form";

        }else if(name.equals("modify")){
            Board board = boardLogicService.readBoard(boardid);
            model.addAttribute("eachboard",board);
            model.addAttribute("board",board);

            return "pages/form";
        }
        else {
            return "redirect:/main";
        }
    }

    @PostMapping("")
    public String postform(@Valid Board board,BindingResult bindingResult, Model model){

        if(bindingResult.hasErrors()){
            model.addAttribute("eachboard",board);
            model.addAttribute("board",board);

            return "pages/form";
        }

        Board newboard = boardLogicService.postservice(board);
        return "redirect:/board/view?id=" + newboard.getId();
    }

}
