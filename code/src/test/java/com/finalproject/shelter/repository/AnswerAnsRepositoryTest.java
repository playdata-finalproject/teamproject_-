package com.finalproject.shelter.repository;

import com.finalproject.shelter.ShelterApplicationTests;
import com.finalproject.shelter.model.entity.Answer;
import com.finalproject.shelter.model.entity.AnswerAns;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class AnswerAnsRepositoryTest extends ShelterApplicationTests {

    @Autowired
    private AnswerAnsRepository answerAnsRepository;
    @Autowired
    private  AnswerRepository answerRepository;

    private Answer answer1;

    @Test
    public void save(){
        Optional<Answer> answer= answerRepository.findById(1L);

        answer.ifPresent(select->{
            answer1=select;
        });

        for (int i =0; i<10; i++) {
            AnswerAns answerAns = AnswerAns.builder()
                    .nickname("b"+i)
                    .answerText("test"+i)
                    .answer(answer1)
                    .build();
            answerAnsRepository.save(answerAns);
        }
    }
}
