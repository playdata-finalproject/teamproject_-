package com.finalproject.shelter.controller.page.board;

import com.finalproject.shelter.model.entity.Answer;
import com.finalproject.shelter.model.entity.Board;
import com.finalproject.shelter.repository.UserRepository;
import com.finalproject.shelter.service.Logic.AnswerLogicService;
import com.finalproject.shelter.service.Logic.BoardLogicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

    @Autowired
    private UserRepository userRepository;

    @GetMapping("")
    public ModelAndView listview(
            @RequestParam(value = "id",required = false, defaultValue = "0") String id,
            @RequestParam(value = "viewboard",required = false, defaultValue = "0") String viewboard
    ){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        Board eachboard = boardLogicService.readBoardview(id,viewboard);
        List<Answer> answerList = answerLogicService.readAnswer(id);
        List<Board> weekview = boardLogicService.bestweekview(String.valueOf(eachboard.getCategory().getId()));
        List<Board> monthview = boardLogicService.bestmonthview(String.valueOf(eachboard.getCategory().getId()));

        Answer answer = Answer.builder()
                .nickname(userRepository.findByUsername(username).getIdentity())
                .board(eachboard)
                .build();

        return new ModelAndView("/pages/view")
                .addObject("eachboard",eachboard)
                .addObject("Answer",answer)
                .addObject("Answers",answerList)
                .addObject("weekview",weekview)
                .addObject("monthview",monthview);
    }

    @PostMapping("/answer")
    public String postanswer(@ModelAttribute Answer answer){

        Answer answer1 = answerLogicService.save(answer);

        return "redirect:/board/view?id=" + answer1.getBoard().getId();
    }
}
