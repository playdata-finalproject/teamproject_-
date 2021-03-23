package com.finalproject.shelter.repository;

import com.finalproject.shelter.ShelterApplicationTests;
import com.finalproject.shelter.model.entity.Categorytable;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

public class CategorytableRepositoryTest extends ShelterApplicationTests {

    private static final Logger log = Logger.getLogger(CategorytableRepositoryTest.class.getName());
    @Autowired
    private CategorytableRepository categorytableRepository;

    @DisplayName("카테고리 테이블 작성 테스트")
    @Test
    @Transactional
    public void create(){
        Categorytable categorytable = Categorytable.builder()
                .title("aaa")
                .build();
        Categorytable newcategorytable = categorytableRepository.save(categorytable);
        Assertions.assertTrue(categorytable.equals(newcategorytable));
    }

    @DisplayName("카테고리 테이블 조회 테스트")
    @Test
    @Transactional
    public void read(){
        List<Categorytable> categorytableList = categorytableRepository.findAll();
        Assertions.assertFalse(categorytableList.isEmpty());

        categorytableList.stream().forEach(select->{
            log.info(select.toString());
        });
    }

    @DisplayName("카테고리 테이블 수정 테스트")
    @Test
    @Transactional
    public void modify(){

        Optional<Categorytable> categorytable = categorytableRepository.findById(1L);
        Assertions.assertTrue(categorytable.isPresent());
        log.info(categorytable.toString());

        categorytable.ifPresent(select->{
            select.setTitle("languageboard");
            categorytableRepository.save(select);
        });

        Optional<Categorytable> categorytable1 = categorytableRepository.findById(1L);
        Assertions.assertTrue(categorytable1.isPresent());

        Assertions.assertTrue(categorytable.equals(categorytable1));
    }

    @DisplayName("카테고리 테이블 삭제 테스트")
    @Test
    @Transactional
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
