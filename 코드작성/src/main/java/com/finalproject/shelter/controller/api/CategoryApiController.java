package com.finalproject.shelter.controller.api;

import com.finalproject.shelter.model.entity.Category;
import com.finalproject.shelter.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryApiController {

    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping("/title/{id}")
    public List<Category> All(@PathVariable Long id){

        List<Category> categoryList = categoryRepository.findCategoryByCategorytableId(id);

        return categoryList;
    }
}
