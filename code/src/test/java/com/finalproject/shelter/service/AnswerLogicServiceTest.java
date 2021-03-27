package com.finalproject.shelter.service;

import com.finalproject.shelter.ShelterApplicationTests;
import com.finalproject.shelter.model.entity.Answer;
import com.finalproject.shelter.repository.AnswerRepository;
import com.finalproject.shelter.repository.BoardRepository;
import com.finalproject.shelter.service.Logic.AnswerLogicService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@Slf4j
public class AnswerLogicServiceTest extends ShelterApplicationTests {

    @Autowired
    private AnswerLogicService answerLogicService;

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private BoardRepository boardRepository;

    @DisplayName("http링크에 href 태그 삽입 테스트")
    @Test
    public void readAnswer(){
        Answer answer = Answer.builder()
                .nickname("test1234")
                .answerText("네이버 주소 : http://www.naver.com")
                .board(boardRepository.getOne(3L))
                .build();

        Answer newanswer = answerRepository.save(answer);
        Assertions.assertFalse(newanswer.getAnswerText().contains("<a style"));

        List<Answer> answerList = answerLogicService.readAnswer("3");
        Assertions.assertFalse(answerList.isEmpty());

        if(answerList!=null){
            answerList.stream().forEach(select->{
                Assertions.assertTrue(select.getAnswerText().contains("<a style"));
            });
        }
    }


    @Test
    public void save(){

    }

    @Test
    public void delete(){

    }

}
