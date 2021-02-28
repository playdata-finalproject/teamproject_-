package com.finalproject.shelter.repository;

import com.finalproject.shelter.model.entity.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnswerRepository extends JpaRepository<Answer,Long> {

    List<Answer> findAllByBoardId(Long id);

    List<Answer> findAllByUseranwserId(Long id);
}
