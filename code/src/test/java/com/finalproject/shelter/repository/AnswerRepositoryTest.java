package com.finalproject.shelter.repository;

import com.finalproject.shelter.ShelterApplicationTests;
import com.finalproject.shelter.model.entity.Answer;
import com.finalproject.shelter.model.entity.Board;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class AnswerRepositoryTest extends ShelterApplicationTests {

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private BoardRepository boardRepository;

    private Board board1;

    @Test
    public void create(){

        Optional<Board> board = boardRepository.findBoardById(2L);

        board.ifPresent(select->{
            board1=select;
        });
        for (int i =5; i<9; i++) {
            Answer answer = Answer.builder()
                    .nickname("aa")
                    .answerText("test"+i)
                    .board(board1)
                    .build();
            Answer newanswer = answerRepository.save(answer);
            System.out.println(newanswer);
        }

    }

    @Test
    public void find(){

        List<Answer> answerList = answerRepository.findAnswerByBoardId(2L);

        if(answerList!=null){
            answerList.stream().forEach(select->{
                System.out.println(select);
            });
        }

    }

}
