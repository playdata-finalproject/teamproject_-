package com.finalproject.shelter.controller.page.board;

import com.finalproject.shelter.model.entity.Board;
import com.finalproject.shelter.service.Logic.AnswerLogicService;
import com.finalproject.shelter.service.Logic.BoardLogicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/board/form")
public class BoardFormPageController {

    @Autowired
    private BoardLogicService boardLogicService;

    @GetMapping("")
    public ModelAndView writeview(HttpServletRequest request, @RequestParam("name") String name,
                                  @RequestParam(required = false,defaultValue = "0")String boardid){

        String id = request.getParameter("id");
        Board board = boardLogicService.readBoard(id);
        Board board1 = Board.builder()
                .category(board.getCategory())
                .build();

        if (name.equals("write")){
            return new ModelAndView("pages/form")
                    .addObject("eachboard",board)
                    .addObject("board",board1);
        }else{
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
