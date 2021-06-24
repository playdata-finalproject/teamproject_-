package com.finalproject.shelter.presentation.settingform;

import com.finalproject.shelter.business.service.logic.BoardLogicService;
import com.finalproject.shelter.domain.model.entity.noticationDomain.Board;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Component
public class SearchForm {
    private Map<String, Page<Board>> boards;

    @PostConstruct
    public void init() {
        boards = new HashMap<>();
    }
    public void add(BoardLogicService boardLogicService, SearchData searchData) {
        boards.put("title", boardLogicService.findTitle(searchData));
        boards.put("contents", boardLogicService.findContents(searchData));
    }
    public Page<Board> getSearch(String select) {
        return boards.get(select);
    }
}
