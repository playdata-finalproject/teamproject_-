package com.finalproject.shelter.service;

import com.finalproject.shelter.ShelterApplicationTests;
import com.finalproject.shelter.model.entity.Board;
import com.finalproject.shelter.repository.BoardRepository;
import com.finalproject.shelter.repository.BoardRepositoryTest;
import com.finalproject.shelter.service.Logic.BoardLogicService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.logging.Logger;

public class BoardLogicServiceTest extends ShelterApplicationTests {

    @Autowired
    private BoardLogicService boardLogicService;

    @Autowired
    private BoardRepository boardRepository;

    private static final Logger log = Logger.getLogger(BoardRepositoryTest.class.getName());

    @DisplayName("service_category 조회 테스트")
    @Test
    @Transactional
    public void readcategory() throws Exception{
        for (int i=1; i<4; i++) {
            Board board = boardLogicService.readCategory(String.valueOf(i));
            Assertions.assertTrue(board.getCategory().getId().equals(Integer.valueOf(i).longValue()));
        }
        for (int i=100; i<103; i++) {
            Board board = boardLogicService.readCategory(String.valueOf(i));
            Assertions.assertTrue(board==null);
        }
    }

    @DisplayName("service_readboard id 조회 테스트")
    @Test
    @Transactional
    public void readboard() throws Exception{
        for (int i=6; i<9; i++) {
            Board board = boardLogicService.readBoard(String.valueOf(i));
            Assertions.assertTrue(board.getId().equals(Integer.valueOf(i).longValue()));
        }
        for (int i=1000; i<1005; i++) {
            Board board = boardLogicService.readBoard(String.valueOf(i));
            Assertions.assertTrue(board.equals(new Board()));
        }
    }

    @DisplayName("service_readboardview 전체 조회 테스트")
    @Test
    @Transactional
    public void readBoardview(){
        for(int i=6; i<9; i++) {
            Board board = boardLogicService.readBoardview(String.valueOf(i));
            Assertions.assertFalse(board.equals(boardRepository.findBoardById(Integer.valueOf(i).longValue())));
        }
        for(int i=100; i<105; i++) {
            Board board = boardLogicService.readBoardview(String.valueOf(i));
            Assertions.assertTrue(board.equals(new Board()));
        }
    }

    @DisplayName("service_deleteid 삭제 테스트")
    @Test
    @Transactional
    public void deleteid() throws Exception{
        for (int i=10; i<15; i++){
            Optional<Board> board = boardRepository.findBoardById(Integer.valueOf(i).longValue());
            String id = boardLogicService.deleteid(String.valueOf(i));
            board.ifPresent(select->{
                Assertions.assertFalse(boardLogicService.readBoard(id).equals(select));
            });
            if (board.isEmpty()){
                Assertions.assertTrue(id=="null");
            }
        }
    }

}
