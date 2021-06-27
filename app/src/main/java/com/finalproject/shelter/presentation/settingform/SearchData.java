package com.finalproject.shelter.presentation.settingform;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;


@Component
public class SearchData {

    private Long id;

    private String searchText;

    private Pageable pageable;

    public SearchData setAll(Long id, String searchText, Pageable pageable) {
        this.id = id;
        this.searchText = searchText;
        this.pageable = pageable;
        return this;
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSearchText() {
        return searchText;
    }

    public void setSearchText(String searchText) {
        this.searchText = searchText;
    }

    public Pageable getPageable() {
        return pageable;
    }

    public void setPageable(Pageable pageable) {
        this.pageable = pageable;
    }
}
