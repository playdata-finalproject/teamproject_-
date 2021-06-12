package com.finalproject.shelter.domain.repository;

import com.finalproject.shelter.ShelterApplicationTests;
import com.finalproject.shelter.domain.model.entity.noticationDomain.Category;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
public class CategoryRepositoryTest extends ShelterApplicationTests {

    private static final Logger log = Logger.getLogger(CategoryRepositoryTest.class.getName());

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CategorytableRepository categorytableRepository;

    @DisplayName("레코드 생성 테스트")
    @Test
    public void create(){
        Category category = Category.builder()
                .title("test")
                .categorytable(categorytableRepository.getOne(1L))
                .build();

        Category newcategory = categoryRepository.save(category);

        Assertions.assertTrue(category.equals(newcategory));

    }
    @Test
    public void findById(){

        Optional<Category> test = categoryRepository.findById(1L);
        test.ifPresent(select->{
            System.out.println(select);
        });

    }

    @DisplayName("레코드 Categorytable id 조회 테스트")
    @Test
    public void findCategoryId(){

        List<Category> category = categoryRepository.findCategoryByCategorytableId(1L);

        Assertions.assertFalse(category.isEmpty());

        category.stream().forEach(select -> {
            assertThat(select.getCategorytable().getId()).isEqualTo(1L);
        });
    }
}
