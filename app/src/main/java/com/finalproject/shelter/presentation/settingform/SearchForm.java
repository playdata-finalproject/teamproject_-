package com.finalproject.shelter.presentation.settingform;

import com.finalproject.shelter.business.service.logic.BoardLogicService;
import com.finalproject.shelter.domain.model.entity.noticationDomain.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;

@Component
public class SearchForm {
    private HashMap<String, Page<Board>> boards;

    @PostConstruct
    public void init() {
        boards = new HashMap<>();
    }

    public void add(BoardLogicService boardLogicService, String id, String searchText, Pageable pageable) {
        boards.put("title", boardLogicService.findTitle(id, searchText, pageable));
        boards.put("contents", boardLogicService.findContents(id, searchText, pageable));
    }

    public Page<Board> getSearch(String select) {
        return boards.get(select);
    }
}
