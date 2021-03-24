package com.finalproject.shelter.service.Logic;

import com.finalproject.shelter.model.entity.Answer;
import com.finalproject.shelter.model.entity.Board;
import com.finalproject.shelter.repository.AccountRepository;
import com.finalproject.shelter.repository.AnswerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

    public Answer writeuserinfo(Board eachboard, AccountRepository accountRepository){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String identity = authentication.getName();

        Answer answer = Answer.builder()
                .nickname(accountRepository.findByIdentity(identity).getIdentity()) //Error
                .board(eachboard)
                .build();

        return answer;
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
