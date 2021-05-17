package com.finalproject.shelter.domainModelLayer.repository;

import com.finalproject.shelter.domainModelLayer.model.entity.noticationDomain.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AnswerRepository extends JpaRepository<Answer,Long> {

    List<Answer> findAnswerByBoardId(Long id);

    Optional<Answer> findAnswerById(Long id);
}
