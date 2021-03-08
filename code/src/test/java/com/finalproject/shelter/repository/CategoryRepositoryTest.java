package com.finalproject.shelter.repository;

import com.finalproject.shelter.ShelterApplicationTests;
import com.finalproject.shelter.model.entity.Category;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


public class CategoryRepositoryTest extends ShelterApplicationTests {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CategorytableRepository categorytableRepository;

    @Test
    public void create(){

        Category category = Category.builder()
                .title("Q&A")
                .categorytable(categorytableRepository.getOne(6L))
                .build();
        categoryRepository.save(category);
    }

    @Test
    public void read(){
        List<Category> category = categoryRepository.findCategoryByCategorytableId(2L);
        Assertions.assertFalse(category.isEmpty());

        category.stream().forEach(select->{
            System.out.println(select);
        });

    }
}
