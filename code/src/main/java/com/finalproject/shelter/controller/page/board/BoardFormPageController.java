package com.finalproject.shelter.controller.page.board;

import com.finalproject.shelter.model.entity.Account;
import com.finalproject.shelter.model.entity.Board;
import com.finalproject.shelter.repository.AccountRepository;
import com.finalproject.shelter.service.Logic.BoardLogicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/board/form")
public class BoardFormPageController {

    @Autowired
    private BoardLogicService boardLogicService;

    @Autowired
    private AccountRepository accountRepository;

    @GetMapping("")
    public ModelAndView writeview(@RequestParam(value = "boardid",defaultValue = "") String boardid,
                                  @RequestParam(value = "name",defaultValue = "") String name,
                                  @RequestParam(value = "categoryid",defaultValue = "") String categoryid){

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

            return new ModelAndView("pages/form")
                    .addObject("eachboard",board)
                    .addObject("board",board1);

        }else{
            Board board = boardLogicService.readBoard(boardid);
            return new ModelAndView("pages/form")
                    .addObject("eachboard",board)
                    .addObject("board",board);
        }
    }

    @PostMapping("")
    public String postform(@ModelAttribute Board board){
        Board newboard = boardLogicService.postservice(board);
        return "redirect:/board/view?id=" + newboard.getId();
    }

}
