package com.finalproject.shelter.repository;

import com.finalproject.shelter.ShelterApplicationTests;
import com.finalproject.shelter.model.entity.Answer;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@Slf4j
public class AnswerRepositoryTest extends ShelterApplicationTests {

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private BoardRepository boardRepository;

    @DisplayName("repository create 확인")
    @Test
    public void create(){

        Answer answer = Answer.builder()
                .nickname("aa")
                .answerText("test")
                .board(boardRepository.getOne(3L))
                .build();

        Answer newanswer = answerRepository.save(answer);

        assertThat(newanswer.getNickname()).isEqualTo(answer.getNickname());
        assertThat(newanswer.getAnswerText()).isEqualTo(answer.getAnswerText());
        assertThat(newanswer.getBoard()).isEqualTo(answer.getBoard());
    }

    @DisplayName("repository readboardid 확인")
    @Test
    public void findBoardid(Long id){
        List<Answer> answerList = answerRepository.findAnswerByBoardId(id);

        if(answerList!=null){
            answerList.stream().forEach(select->{
                assertThat(select.getBoard().getId()).isEqualTo(id);
            });
        }else{
            log.error("게시판 없음");
        }
    }

    @DisplayName("repository readid 확인")
    @Test
    public void findid(){
        Optional<Answer> answer = answerRepository.findAnswerById(5L);

        answer.ifPresent(select->{
            assertThat(select.getId()).isEqualTo(5L);
        });
        if(answer.isEmpty()){
            log.error("answer값 없음");
        }
    }

    @DisplayName("repository delete 확인")
    @Test
    public void delete(){
        Optional<Answer> answer = answerRepository.findAnswerById(5L);

        answer.ifPresent(select->{
            answerRepository.delete(select);
            Optional<Answer> newanswer = answerRepository.findAnswerById(5L);
            Assertions.assertTrue(newanswer.isEmpty());
        });
        if(answer.isEmpty()){
            log.error("지울게 없음");
        }

    }

}
