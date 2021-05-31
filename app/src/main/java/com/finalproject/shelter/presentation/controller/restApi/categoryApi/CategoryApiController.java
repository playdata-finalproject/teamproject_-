package com.finalproject.shelter.presentation.controller.restApi.categoryApi;

import com.finalproject.shelter.domain.model.entity.noticationDomain.Category;
import com.finalproject.shelter.domain.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryApiController {

    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping("/title/{id}")
    public List<Category> all(@PathVariable Long id) {
        List<Category> categories = categoryRepository.findCategoryByCategorytableId(id);
        return categories;
    }
}
