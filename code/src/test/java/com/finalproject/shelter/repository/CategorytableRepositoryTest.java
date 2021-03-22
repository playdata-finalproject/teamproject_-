package com.finalproject.shelter.repository;

import com.finalproject.shelter.ShelterApplicationTests;
import com.finalproject.shelter.model.entity.Categorytable;
import org.junit.jupiter.api.Assertions;
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

    @Test
    @Transactional
    public void create(){
        Categorytable categorytable = Categorytable.builder()
                .title("aaa")
                .build();
        Categorytable newcategorytable = categorytableRepository.save(categorytable);
        Assertions.assertTrue(categorytable.equals(newcategorytable));
    }

    @Test
    @Transactional
    public void read(){
        List<Categorytable> categorytableList = categorytableRepository.findAll();
        Assertions.assertFalse(categorytableList.isEmpty());

        categorytableList.stream().forEach(select->{
            log.info(select.toString());
        });
    }

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
