package com.finalproject.shelter.service;

import com.finalproject.shelter.ShelterApplicationTests;
import com.finalproject.shelter.model.entity.Board;
import com.finalproject.shelter.repository.BoardRepositoryTest;
import com.finalproject.shelter.service.Logic.BoardLogicService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.logging.Logger;

public class BoardLogicServiceTest extends ShelterApplicationTests {

    @Autowired
    private BoardLogicService boardLogicService;

    private static final Logger log = Logger.getLogger(BoardRepositoryTest.class.getName());

    @Test
    @Transactional
    public void readcategory() throws Exception{
        for (int i=15; i<18; i++) {
            Board board = boardLogicService.readCategory(String.valueOf(i));
            log.info(board.toString());
        }
    }

    @Test
    @Transactional
    public void readboard() throws Exception{
        for (int i=50; i<52; i++) {
            Board board = boardLogicService.readBoard(String.valueOf(i));
            log.info(board.toString());
        }
    }

    @Test
    @Transactional
    public void readBoardview(){
        Board board = boardLogicService.readBoardview("7");
        log.info(board.toString());
    }

    @Test
    @Transactional
    public void deletepageController() throws Exception{
        for (int i=10; i<15; i++){
            String id = boardLogicService.deleteid(String.valueOf(i));
            log.info(id);
        }
    }

}
