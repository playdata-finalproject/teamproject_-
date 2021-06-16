package com.finalproject.shelter.presentation.settingform;

import com.finalproject.shelter.business.service.logic.BoardLogicService;
import com.finalproject.shelter.domain.model.entity.noticationDomain.Board;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;

@Component
public class SearchForm {
    private HashMap<String, Page<Board>> list;

    @PostConstruct
    public void init(){
        list = new HashMap<>();
    }
    public void add(BoardLogicService boardLogicService, String id, String searchText, Pageable pageable){
        list.put("",boardLogicService.findTitle(id,searchText,pageable));
        list.put("title",boardLogicService.findTitle(id,searchText,pageable));
        list.put("contents",boardLogicService.findContents(id,searchText,pageable));
    }
    public Page<Board> getSearch(String select) {
        return list.get(select);
    }
}
