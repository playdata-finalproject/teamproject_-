package com.finalproject.shelter.repository;

import com.finalproject.shelter.ShelterApplicationTests;
import com.finalproject.shelter.model.entity.Answer;
import com.finalproject.shelter.model.entity.Board;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

public class AnswerRepositoryTest extends ShelterApplicationTests {

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private BoardRepository boardRepository;

    private Board board1;

    private static final Logger log = Logger.getLogger(BoardRepositoryTest.class.getName());
    @DisplayName("댓글 작성 확인")
    @Test
    public void create(){
        for (int i =5; i<9; i++) {
            Answer answer = Answer.builder()
                    .nickname("aa")
                    .answerText("test"+i)
                    .board(boardRepository.getOne(1L))
                    .build();
            Answer newanswer = answerRepository.save(answer);
            log.info(newanswer.toString());
        }
    }
    @DisplayName("댓글 검색되는지 테스트")
    @Test
    public void find(){

        List<Answer> answerList = answerRepository.findAnswerByBoardId(2L);

        if(answerList!=null){
            answerList.stream().forEach(select->{
                log.info(select.toString());
            });
        }
    }

}
