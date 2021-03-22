package com.finalproject.shelter.repository;

import com.finalproject.shelter.ShelterApplicationTests;
import com.finalproject.shelter.model.entity.Category;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.logging.Logger;

public class CategoryRepositoryTest extends ShelterApplicationTests {

    private static final Logger log = Logger.getLogger(CategoryRepositoryTest.class.getName());

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CategorytableRepository categorytableRepository;

    @Test
    @Transactional
    public void create(){
        for (int i=0; i<10; i++){

            Category category = Category.builder()
                    .title("test"+i)
                    .categorytable(categorytableRepository.getOne(1L))
                    .build();
            Category newcategory = categoryRepository.save(category);

            Assertions.assertTrue(category.equals(newcategory));
        }
    }

    @Test
    @Transactional
    public void read() {
        List<Category> category = categoryRepository.findCategoryByCategorytableId(1L);

        // query 를 더 적게 날린다.
        Assertions.assertFalse(category.isEmpty());

        category.stream().forEach(select -> {
            log.info(select.toString());
        });

    }
}
