package com.finalproject.shelter.controller.page.board;

import com.finalproject.shelter.model.entity.Account;
import com.finalproject.shelter.model.entity.Board;
import com.finalproject.shelter.repository.AccountRepository;
import com.finalproject.shelter.service.Logic.BoardLogicService;
//import com.finalproject.shelter.validator.BoardValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/board/form")
public class BoardFormPageController {

    @Autowired
    private BoardLogicService boardLogicService;

    @Autowired
    private AccountRepository accountRepository;

//    @Autowired
//    private BoardValidator boardValidator;

    @GetMapping("")
    public String writeview(
            @RequestParam(value = "boardid",defaultValue = "") String boardid,
            @RequestParam(value = "name",defaultValue = "") String name,
            @RequestParam(value = "categoryid",defaultValue = "") String categoryid,
            Model model
    ){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        Account account = accountRepository.findByUsername(username);

        if (name.equals("write")){
            Board board = boardLogicService.readCategory(categoryid);
            Board board1 = Board.builder()
                    .nickname(account.getIdentity())
                    .user(account)
                    .category(board.getCategory())
                    .build();
            model.addAttribute("eachboard",board);
            model.addAttribute("board",board1);

            return "pages/form";

        }else{
            Board board = boardLogicService.readBoard(boardid);
            model.addAttribute("eachboard",board);
            model.addAttribute("board",board);

            return "pages/form";
        }
    }

    @PostMapping("")
    public String postform(@Valid Board board,BindingResult bindingResult, Model model){

        //boardValidator.validate(board,bindingResult);

        if(bindingResult.hasErrors()){
            model.addAttribute("eachboard",board);
            model.addAttribute("board",board);

            return "pages/form";
        }

        Board newboard = boardLogicService.postservice(board);
        return "redirect:/board/view?id=" + newboard.getId();
    }

}
