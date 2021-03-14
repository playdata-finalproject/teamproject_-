package com.finalproject.shelter.controller.page.board;

import com.finalproject.shelter.model.entity.Answer;
import com.finalproject.shelter.model.entity.Board;
import com.finalproject.shelter.service.Logic.AnswerLogicService;
import com.finalproject.shelter.service.Logic.BoardLogicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/board/view")
public class BoardViewPageController {

    @Autowired
    private BoardLogicService boardLogicService;

    @Autowired
    private AnswerLogicService answerLogicService;

    @GetMapping("")
    public ModelAndView listview(
            @RequestParam(value = "id",required = false, defaultValue = "0") String id,
            @RequestParam(value = "viewboard",required = false, defaultValue = "0") String viewboard
    ){

        Board eachboard = boardLogicService.readBoardview(id,viewboard);
        List<Answer> answerList = answerLogicService.readAnswer(id);

        Answer answer = Answer.builder()
                .board(eachboard)
                .build();

        return new ModelAndView("/pages/view")
                .addObject("eachboard",eachboard)
                .addObject("Answer",answer)
                .addObject("Answers",answerList);
    }

    @PostMapping("/answer")
    public String postanswer(@ModelAttribute Answer answer){

        Answer answer1 = answerLogicService.save(answer);

        return "redirect:/board/view?id=" + answer1.getBoard().getId();
    }
}
