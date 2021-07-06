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
            @RequestParam(value = "receiveId", required = false, defaultValue = "0") String receiveId,
            Model model) {
        Board eachBoard = boardLogicService.readBoardview(receiveId);
        Long id = Long.valueOf(receiveId);

        model.addAttribute("Answer", eachBoard);
        model.addAttribute("eachboard", answerLogicService.readUser(eachBoard));
        model.addAttribute("Answers", answerLogicService.readAnswer(id));
        model.addAttribute("weekview", boardLogicService.bestWeekView(getCategoryId(eachBoard)));
        model.addAttribute("monthview", boardLogicService.bestMonthView(getCategoryId(eachBoard)));

        return "pages/view";
    }

    @PostMapping("/answer")
    public String postAnswer(@Valid Answer answer, BindingResult bindingResult, Model model) {
        Long id = answer.getBoard().getId();
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
            @RequestParam(value = "receiveId", required = false, defaultValue = "0") String receiveId,
            @RequestParam(value = "boardId", required = false, defaultValue = "0") String boardId) {
        delete(Long.valueOf(receiveId));
        return "redirect:/board/view?id=" + boardId;
    }

    private void delete(Long id) {
        answerLogicService.delete(id);
    }

    private Long getCategoryId(Board board) {
        return board.getCategory().getId();
    }
}