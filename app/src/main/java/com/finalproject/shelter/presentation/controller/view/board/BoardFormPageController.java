package com.finalproject.shelter.presentation.controller.view.board;

import com.finalproject.shelter.business.service.logic.CategoryLogicService;
import com.finalproject.shelter.domain.model.entity.noticationDomain.Board;
import com.finalproject.shelter.domain.model.entity.noticationDomain.Category;
import com.finalproject.shelter.business.service.logic.BoardLogicService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/board/form")
@Slf4j
public class BoardFormPageController {

    private final BoardLogicService boardLogicService;
    private final CategoryLogicService categoryLogicService;

    public BoardFormPageController(BoardLogicService boardLogicService, CategoryLogicService categoryLogicService){
        this.boardLogicService = boardLogicService;
        this.categoryLogicService = categoryLogicService;
    }

    @Autowired
    private CategoryLogicService categoryLogicService;

    @GetMapping("/write")
    public String create(
            @RequestParam(value = "id", defaultValue = "") String id,
            Model model) {

        Category category = categoryLogicService.findById(Long.parseLong(id));


        model.addAttribute("category", category);
        model.addAttribute("board", boardLogicService.newUserBoard(category));
        return "pages/form";
    }

    @GetMapping("/modify")
    public String modify(
            @RequestParam(value = "id", defaultValue = "") String id,
            Model model) {
        model.addAttribute("eachboard", boardLogicService.readBoard(id));
        model.addAttribute("board", boardLogicService.readBoard(id));
        return "pages/form";
    }

    @PostMapping("")
    public String save(@Valid Board board, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("eachboard", board);
            model.addAttribute("board", board);
            return "pages/form";
        }
        return "redirect:/board/view?id=" + boardLogicService.postservice(board).getId();
    }
}
