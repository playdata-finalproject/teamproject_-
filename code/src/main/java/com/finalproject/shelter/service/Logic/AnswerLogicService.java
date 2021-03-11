package com.finalproject.shelter.service.Logic;

import com.finalproject.shelter.model.entity.Answer;
import com.finalproject.shelter.repository.AnswerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnswerLogicService {

    @Autowired
    private AnswerRepository answerRepository;

    public List<Answer> readAnswer(String id){
        List<Answer> answerList = answerRepository.findAnswerByBoardId(Long.parseLong(id));

        return answerList;
    }

    public Answer save(Answer answer){
        Answer answer1 = Answer.builder()
                .nickname(answer.getNickname())
                .answerText(answer.getAnswerText())
                .board(answer.getBoard())
                .build();

        return answerRepository.save(answer1);
    }
}
