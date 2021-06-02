package com.finalproject.shelter.business.service.logic;

import com.finalproject.shelter.domain.model.entity.noticationDomain.Answer;
import com.finalproject.shelter.domain.model.entity.noticationDomain.Board;
import com.finalproject.shelter.domain.repository.AccountRepository;
import com.finalproject.shelter.domain.repository.AnswerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Slf4j
public class AnswerLogicService {

    @Autowired
    private AnswerRepository answerRepository;

    public List<Answer> readAnswer(String id){
        List<Answer> answerList = answerRepository.findAnswerByBoardId(Long.parseLong(id));

        answerList.stream().forEach(select->{
            String REGEX = "\\b(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";

            Pattern pattern = Pattern.compile(REGEX);
            Matcher matcher = pattern.matcher(select.getAnswerText());
            while (matcher.find()) {
                select.setAnswerText(select.getAnswerText().replace(matcher.group(),"<a style='color:skyblue' href="+matcher.group()+">"+matcher.group()+"</a>"));
            }
        });

        return answerList;
    }
    
    public Answer readUser(Board eachboard, AccountRepository accountRepository){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        Answer answer = Answer.builder()
                .nickname(accountRepository.findByUsername(username).getIdentity()) //Error
                .board(eachboard)
                .build();

        return answer;
    }

    public Answer save(Answer answer){
        String regex = "<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>";
        String tagRemove=answer.getAnswerText().replaceAll(regex, "<>안에 한글만 사용할수 있습니다.");
        answer.setAnswerText(tagRemove);

        Answer answer1 = Answer.builder()
                .nickname(answer.getNickname())
                .answerText(answer.getAnswerText())
                .board(answer.getBoard())
                .build();

        return answerRepository.save(answer1);
    }

    public void delete(String id){
        Optional<Answer> answer = answerRepository.findAnswerById(Long.parseLong(id));

        answer.ifPresent(select->{
            answerRepository.delete(select);
        });

        if (answer.isEmpty()){
            log.error("answer is empty");
        }
    }
}
