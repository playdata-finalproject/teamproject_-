package com.finalproject.shelter.presentation.controller.view.board;

import com.finalproject.shelter.domain.model.entity.noticationDomain.Board;
import com.finalproject.shelter.business.service.logic.BoardLogicService;
import com.finalproject.shelter.presentation.settingform.SearchForm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/board")
public class BoardPageController {

    private final BoardLogicService boardLogicService;
    private final SearchForm searchForm;

    public BoardPageController(BoardLogicService boardLogicService, SearchForm searchForm) {
        this.boardLogicService = boardLogicService;
        this.searchForm = searchForm;
    }

    @GetMapping("")
    public String readAll(@RequestParam(value = "id") String id,
                          @PageableDefault(size = 10) Pageable pageable,
                          @RequestParam(value = "select", required = false, defaultValue = "") String select,
                          @RequestParam(value = "searchText", required = false, defaultValue = "") String searchText,
                          Model model
    ) {
        searchForm.add(boardLogicService,id,searchText,pageable);
        Page<Board> boards = searchForm.getSearch(select);

        model.addAttribute("boards", boards);
        model.addAttribute("eachboard", boardLogicService.readCategory(id));
        model.addAttribute("startPage", Math.max(1, boards.getPageable().getPageNumber() - 4));
        model.addAttribute("endPage", Math.min(boards.getTotalPages(), boards.getPageable().getPageNumber() + 4));
        model.addAttribute("weekview", boardLogicService.bestweekview(id));
        model.addAttribute("mothview", boardLogicService.bestmonthview(id));

        return "pages/list";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam(value = "id") String id) {
        boardLogicService.deleteid(id);
        return "redirect:/main";
    }

}