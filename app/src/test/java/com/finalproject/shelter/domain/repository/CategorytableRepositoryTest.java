package com.finalproject.shelter.domain.repository;

import com.finalproject.shelter.ShelterApplicationTests;
import com.finalproject.shelter.domain.model.entity.noticationDomain.Categorytable;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Slf4j
@Transactional
public class CategorytableRepositoryTest extends ShelterApplicationTests {

    private final CategorytableRepository categorytableRepository;

    public CategorytableRepositoryTest(CategorytableRepository categorytableRepository) {
        this.categorytableRepository = categorytableRepository;
    }

    @DisplayName("카테고리 테이블 작성 테스트")
    @Test
    public void create(){
        Categorytable categorytable = Categorytable.builder()
                .title("aaa")
                .build();
        Categorytable newcategorytable = categorytableRepository.save(categorytable);
        Assertions.assertTrue(categorytable.equals(newcategorytable));
    }

    @Test
    public void findById(){
        Optional<Categorytable> categorytable = categorytableRepository.findById(1L);
        categorytable.ifPresent(select->{
            Assertions.assertEquals(select.getId(),1L);
        });
    }

    @DisplayName("카테고리 테이블 삭제 테스트")
    @Test
    public void delete(){
        Optional<Categorytable> categorytable = categorytableRepository.findById(1L);
        Assertions.assertTrue(categorytable.isPresent());

        categorytable.ifPresent(select->{
            categorytableRepository.delete(select);
        });
        Optional<Categorytable> categorytable1 = categorytableRepository.findById(1L);
        Assertions.assertTrue(categorytable1.isEmpty());
    }
}
