package com.finalproject.shelter.presentation.controller.view.board;

import com.finalproject.shelter.domain.model.entity.noticationDomain.Board;
import com.finalproject.shelter.business.service.logic.BoardLogicService;
import com.finalproject.shelter.presentation.settingform.SearchData;
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
    private final int pageRange = 4;
    private final int pageMinNumber = 1;

    public BoardPageController(BoardLogicService boardLogicService, SearchForm searchForm, SearchData searchData) {
        this.boardLogicService = boardLogicService;
        this.searchForm = searchForm;
    }

    @GetMapping("")
    public String readAll(@RequestParam(value = "id") String id,
                          @PageableDefault(size = 10) Pageable pageable,
                          Model model
    ) {
        Page<Board> boards = boardLogicService.findCategorys(id, pageable);

        model.addAttribute("boards", boards);
        model.addAttribute("eachboard", boardLogicService.readCategory(id));
        model.addAttribute("startPage", Math.max(pageMinNumber, boards.getPageable().getPageNumber() - pageRange));
        model.addAttribute("endPage", Math.min(boards.getTotalPages(), boards.getPageable().getPageNumber() + pageRange));
        model.addAttribute("weekview", boardLogicService.bestWeekView(id));
        model.addAttribute("mothview", boardLogicService.bestMonthView(id));

        return "pages/list";
    }

    @GetMapping("/search")
    public String readSearchAll(@RequestParam(value = "id") String id,
                                @PageableDefault(size = 10) Pageable pageable,
                                @RequestParam(value = "select", required = false, defaultValue = "") String select,
                                @RequestParam(value = "searchText", required = false, defaultValue = "") String searchText,
                                Model model
    ) {
        searchForm.DataSet(Long.valueOf(id), searchText, pageable);
        searchForm.add(boardLogicService);
        Page<Board> boards = searchForm.getSearch(select);

        model.addAttribute("boards", boards);
        model.addAttribute("eachboard", boardLogicService.readCategory(id));
        model.addAttribute("startPage", Math.max(pageMinNumber, boards.getPageable().getPageNumber() - pageRange));
        model.addAttribute("endPage", Math.min(boards.getTotalPages(), boards.getPageable().getPageNumber() + pageRange));
        model.addAttribute("weekview", boardLogicService.bestWeekView(id));
        model.addAttribute("mothview", boardLogicService.bestMonthView(id));

        return "pages/list";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam(value = "id") String id) {
        boardLogicService.deleteid(id);
        return "redirect:/main";
    }

}