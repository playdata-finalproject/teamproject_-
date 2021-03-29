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

    @DisplayName("레코드 생성 테스트")
    @Test
    public void create(){

        Answer answer = Answer.builder()
                .nickname("aa")
                .answerText("test")
                .board(boardRepository.getOne(3L))
                .build();

        Answer newanswer = answerRepository.save(answer);

        Assertions.assertTrue(newanswer.equals(answer));

        assertThat(newanswer.getNickname()).isEqualTo(answer.getNickname());
        assertThat(newanswer.getAnswerText()).isEqualTo(answer.getAnswerText());
        assertThat(newanswer.getBoard()).isEqualTo(answer.getBoard());
    }

    @DisplayName("레코드 boardid 찾기 테스트")
    @Test
    public void findBoardid(){
        List<Answer> answerList = answerRepository.findAnswerByBoardId(6L);
        Assertions.assertFalse(answerList.isEmpty());

        if(answerList!=null){
            answerList.stream().forEach(select->{
                assertThat(select.getBoard().getId()).isEqualTo(6L);
            });
        }
    }

    @DisplayName("레코드 answerid 찾기 테스트")
    @Test
    public void findid(){
        Optional<Answer> answer = answerRepository.findAnswerById(5L);
        Assertions.assertTrue(answer.isPresent());

        answer.ifPresent(select->{
            assertThat(select.getId()).isEqualTo(5L);
        });
    }

    @DisplayName("레코드 삭제 테스트")
    @Test
    public void delete(){
        Optional<Answer> answer = answerRepository.findAnswerById(5L);
        Assertions.assertTrue(answer.isPresent());

        answer.ifPresent(select->{
            answerRepository.delete(select);
        });

        Optional<Answer> newanswer = answerRepository.findAnswerById(5L);
        Assertions.assertTrue(newanswer.isEmpty());


    }

}
