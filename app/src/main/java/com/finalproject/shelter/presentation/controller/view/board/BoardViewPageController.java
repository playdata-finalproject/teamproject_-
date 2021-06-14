package com.finalproject.shelter.presentation.controller.view.board;

import com.finalproject.shelter.domain.model.entity.noticationDomain.Answer;
import com.finalproject.shelter.domain.model.entity.noticationDomain.Board;
import com.finalproject.shelter.business.service.logic.AnswerLogicService;
import com.finalproject.shelter.business.service.logic.BoardLogicService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/board/view")
public class BoardViewPageController {

    private final BoardLogicService boardLogicService;
    private final AnswerLogicService answerLogicService;

    public BoardViewPageController(BoardLogicService boardLogicService, AnswerLogicService answerLogicService) {
        this.answerLogicService = answerLogicService;
        this.boardLogicService = boardLogicService;
    }

    @GetMapping("")
    public String listview(
            @RequestParam(value = "id", required = false, defaultValue = "0") String id,
            Model model) {
        Board eachBoard = boardLogicService.readBoardview(id);

        model.addAttribute("Answer", eachBoard);
        model.addAttribute("eachboard", answerLogicService.readUser(eachBoard));
        model.addAttribute("Answers", answerLogicService.readAnswer(id));
        model.addAttribute("weekview", boardLogicService.bestweekview(getCategoryId(eachBoard)));
        model.addAttribute("monthview", boardLogicService.bestmonthview(getCategoryId(eachBoard)));

        return "pages/view";
    }

    @PostMapping("/answer")
    public String postAnswer(@Valid Answer answer, BindingResult bindingResult, Model model) {
        String id = String.valueOf(answer.getBoard().getId());
        if (bindingResult.hasErrors()) {
            model.addAttribute("eachboard", answer.getBoard());
            model.addAttribute("Answer", answer);
            model.addAttribute("Answers", answerLogicService.readAnswer(id));
            return "pages/view";
        }
        Answer answer1 = answerLogicService.save(answer);
        return "redirect:/board/view?id=" + answer1.getBoard().getId();
    }

    @GetMapping("/answer/delete")
    public String deleteAnswer(
            @RequestParam(value = "id", required = false, defaultValue = "0") String id,
            @RequestParam(value = "boardid", required = false, defaultValue = "0") String boardid) {
        answerLogicService.delete(id);
        return "redirect:/board/view?id=" + boardid;
    }

    private String getCategoryId(Board board) {
        String categoryId = String.valueOf(board.getCategory().getId());
        return categoryId;
    }
}