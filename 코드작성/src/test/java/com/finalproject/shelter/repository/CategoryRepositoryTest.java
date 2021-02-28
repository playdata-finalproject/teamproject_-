package com.finalproject.shelter.repository;

import com.finalproject.shelter.ShelterApplicationTests;
import com.finalproject.shelter.model.entity.Category;
import com.finalproject.shelter.model.entity.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;


public class CategoryRepositoryTest extends ShelterApplicationTests {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void create(){
        Optional<User> user = userRepository.findById(2L);

        Assertions.assertNotNull(user);

        user.ifPresent(select->{
            Category category = Category.builder()
                    .title("python4")
                    .usercategory(select)
                    .build();
            Category newcategory = categoryRepository.save(category);
        });
    }

    @Test
    public void read(){
        Optional<Category> category = categoryRepository.findById(1L);
        Assertions.assertNotNull(category);

        category.ifPresent(select->{
            System.out.println(select);
        });
    }


    @Test
    public void readUsercategory(){

        List<Category> category = categoryRepository.findAllByUsercategoryId(1L);
        Assertions.assertNotNull(category);

        category.stream().forEach(select->{
            System.out.println(select);
        });

    }
}
