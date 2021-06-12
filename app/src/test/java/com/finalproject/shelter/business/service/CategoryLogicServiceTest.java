package com.finalproject.shelter.business.service;

import com.finalproject.shelter.ShelterApplicationTests;
import com.finalproject.shelter.business.service.logic.CategoryLogicService;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.NoSuchElementException;


public class CategoryLogicServiceTest extends ShelterApplicationTests {

    @Autowired
    private CategoryLogicService categoryLogicService;

    @DisplayName("findById Test")
    @Test
    public void findById() {
        long errorId = 1000L;
        long id = 1L;

        Assertions.assertThrows(NoSuchElementException.class, () -> categoryLogicService.findById(errorId));
        Assertions.assertEquals(categoryLogicService.findById(id).getId(),1L);
    }
}
